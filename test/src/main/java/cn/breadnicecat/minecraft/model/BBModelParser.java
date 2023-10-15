package cn.breadnicecat.minecraft.model;

import cn.breadnicecat.minecraft.utils.Int2ObjectPair;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.breadnicecat.minecraft.Main.GSON;
import static cn.breadnicecat.minecraft.utils.Utils.substringByRegex;

/**
 * Created in 2023/8/23 21:22
 * Project: candycraftce
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class BBModelParser {
	private BBModelParser() {
	}


	/**
	 * Exported for Minecraft version 1.17 or later with Mojang mappings
	 */
	public static JsonObject toCat(List<String> rawLines) throws IOException {
		//行数->行
		ArrayList<Int2ObjectPair<String>> lines = new ArrayList<>();
		//填充行(并将部分不在一行的代码合并到一行)
		{
			StringBuilder line = new StringBuilder();
			for (int i = 0; i < rawLines.size(); i++) {
				String s = rawLines.get(i).trim();
				if (s.startsWith("//") || s.equals("@Override") || s.equals("{") || s.equals("}")) {//满足条件即忽略此行
					continue;
				}
				line.append(s);
				if (s.endsWith(";") || s.endsWith("{")) {//换行
					lines.add(Int2ObjectPair.of(i, line.toString()));
					line.setLength(0);
				}
			}
		}
		CatEntityModel bncmodel = new CatEntityModel();
		for (Int2ObjectPair<String> ldef : lines) {
//			int lc = ldef.first();
			String l = ldef.second();
			if (l.matches("public class .+? \\{")//满足条件即忽略此行
					|| l.matches("private final ModelPart .+?;")
					|| l.matches("public .+?\\(ModelPart root\\) \\{")
					|| l.equals("public static LayerDefinition createBodyLayer() {")
					|| l.equals("MeshDefinition meshdefinition = new MeshDefinition();")
					|| l.equals("PartDefinition partdefinition = meshdefinition.getRoot();")
					|| l.startsWith("public void setupAnim(")
					|| l.startsWith("public void renderToBuffer(")
					|| l.matches("this\\..+? = root.getChild(.+);")
					|| l.matches(".+?\\.render\\((\\w+(, )?){8}\\);")
			) continue;
			if (l.matches("PartDefinition .+? = .+?;")) {
				l = l.substring(0, l.length() - 1).split("= ")[1];//取= 后的字符串
				String[] split = l.split("[.]", 2);
				String parent = split[0];//第一个参数
				l = split[1];
				if (l.startsWith("addOrReplaceChild(")) {
					l = l.substring(18, l.length() - 1);//除去"addOrReplaceChild("和最后的")"
					//获取addOrReplaceChild的三个参数
					List<String> parts = splitChain(l, ',');
					String name = parts.get(0);
					/*
					"xiabanshen_r1"
					CubeListBuilder.create().texOffs(194, 333).addBox(-5.75F, -15.25F, -15.25F, 7.0F, 16.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(267, 183).addBox(-9.75F, -4.25F, -16.0F, 15.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
					PartPose.offsetAndRotation(2.0F, -32.0F, 13.0F, 0.3054F, 0.0F, 0.0F)
					*/
					//处理调用对象和第一个参数
					if (parent.startsWith("\"") && parent.endsWith("\"")) {
						parent = parent.substring(1, parent.length() - 1);
					}
					if (name.startsWith("\"") && name.endsWith("\"")) {
						name = name.substring(1, name.length() - 1);
					}
					//处理第二个参数
					CatEntityModel.CubeListBuilder builder = null;
					List<String> secParts = splitChain(parts.get(1), '.');
					for (String p : secParts) {
						if (p.equals("CubeListBuilder")) {
						} else if (p.equals("create()")) {
							builder = CatEntityModel.CubeListBuilder.create();
						} else if (p.equals("mirror()")) {
							builder.mirror();
						} else if (p.matches("texOffs\\(\\d+, \\d+\\)")) {
							List<String> params = substringByRegex(p, "(?<=\\(|, )\\d+(?=\\)|, )");
							builder.texOffs(Integer.parseInt(params.get(0)), Integer.parseInt(params.get(1)));
						} else if (p.matches("mirror\\((false|true)\\)")) {
							boolean isMirror = Boolean.parseBoolean(substringByRegex(p, "\\(false|true\\)").get(0));
							builder.mirror(isMirror);
						} else if (p.matches("addBox\\(((-?\\d+\\.?\\d*F)(, |\\))){6}+new CubeDeformation\\(-?\\d+\\.?\\d*F\\)\\)")) {
							List<String> params = substringByRegex(p, "((-?\\d+\\.?\\d*F)(?=, |\\)))+");
							float[] paramsF = new float[7];
							for (int i = 0; i < params.size(); i++) {
								paramsF[i] = Float.parseFloat(params.get(i));
							}
							builder.addBox(paramsF[0], paramsF[1], paramsF[2], paramsF[3], paramsF[4], paramsF[5], new CatEntityModel.CubeDeformation(paramsF[6]));
						} else {
							throw new UnsupportedBlockBenchExportedForgeEntityFormat("Undefined CubeListBuilder Method: " + p, ldef.first(), ldef.second());
						}
					}
					//处理第三个参数
					//获取方法名和参数
					String s = parts.get(2);
					List<Float> params = substringByRegex(s, "(-?\\d+\\.?\\d*F)").stream().map((Float::valueOf)).toList();
					String spl = s.split("\\(", 2)[0];
					CatEntityModel.PartPose pose = switch (spl) {
						case "PartPose.offset" ->
								CatEntityModel.PartPose.offset(params.get(0), params.get(1), params.get(2));
						case "PartPose.rotation" ->
								CatEntityModel.PartPose.rotation(params.get(0), params.get(1), params.get(2));
						case "PartPose.offsetAndRotation" ->
								CatEntityModel.PartPose.offsetAndRotation(params.get(0), params.get(1), params.get(2), params.get(3), params.get(4), params.get(5));
						default ->
								throw new UnsupportedBlockBenchExportedForgeEntityFormat("Undefined PartPose Method: " + spl, ldef.first(), ldef.second());
					};

					bncmodel.addOrReplaceChild(parent.equals("partdefinition") ? null : parent, name, builder, pose);
					System.out.println("add child: " + parent + ":" + name);
				}
			} else if (l.matches("return LayerDefinition\\.create\\(\\w+, \\d+, \\d+\\);")) {
				List<String> params = substringByRegex(l, "(?<=\\(|, ).+?(?=\\)|, )");
				bncmodel.setUV(Integer.parseInt(params.get(1)), Integer.parseInt(params.get(2)));
			} else if (l.matches("public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation\\(new ResourceLocation\\(\".+?\", \".+?\"\\), \".+?\"\\);")) {
//				bncmodel.setName(substringByRegex(l, STRING_REGEX).get(1));
			} else {
				throw new UnsupportedBlockBenchExportedForgeEntityFormat("Undefined Line Operation", ldef.first(), ldef.second());
			}
		}
		bncmodel.validate();
		return GSON.toJsonTree(bncmodel).getAsJsonObject();
	}


	/**
	 * 切分链式语句
	 * <p>
	 * example1:
	 * args:(l=" a.append("abc.xyz").append(1.0D)", by='.')
	 * output:[a,append("abc"),append(1.0D)]
	 * <p>
	 * example2:
	 * args:(l="0.0D, 1.0D, 0.0D, true", by=',')
	 * output:[0.0D, 1.0D, 0.0D, true]
	 */
	private static @NotNull List<String> splitChain(String l, char by) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> parts = new ArrayList<>();
		int k = 0;
		for (char c : l.toCharArray()) {
			switch (c) {
				case '(' -> k++;
				case ')' -> {
					if (k > 0) k--;//防止出现 ")(的情况"
				}
				default -> {
					if (by == c && k == 0) {
						parts.add(sb.toString().trim());
						sb.setLength(0);
						continue;
					}
				}
			}
			sb.append(c);
		}
		if (!sb.isEmpty()) parts.add(sb.toString().trim());//处理最后遗漏的

		return parts;
	}
}
