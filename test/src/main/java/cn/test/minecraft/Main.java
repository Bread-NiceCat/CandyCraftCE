package cn.test.minecraft;

import cn.test.minecraft.model.BBModelParser;
import cn.test.minecraft.model.anim.AnimLoader;
import cn.test.minecraft.model.anim.ModelAnimation;
import cn.test.minecraft.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created in 2023/8/23 20:50
 * Project: Default (Template) Project
 *
 * @author <a href="https://github.com/BreadNiceCat">Bread_NiceCat</a>
 */
public class Main {
	public static final Path GENERATED_DIRECTORY = new File("generated").toPath();
	public static final Gson GSON = new GsonBuilder()
			.disableHtmlEscaping()
			.excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
			.setPrettyPrinting()
			.create();

	public static void main(String[] args) throws IOException {
		long stt = System.currentTimeMillis();
//		parseBBModel("bbmodel", true);
		var a = parseAnim("anim.json.gz");
		System.out.println("generated successfully in " + (System.currentTimeMillis() - stt) + "ms");

		stt = System.currentTimeMillis();
		ModelAnimation animation = a.get("animation.monarch_mecha.general1");
		long att = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			animation.load("xiabanshen", 0.1f);
		}
		long f = (System.currentTimeMillis() - stt);
		System.out.println("test successfully in " + f + " ms");

	}

	private static HashMap<String, ModelAnimation> parseAnim(String source) throws IOException {
		return AnimLoader.loadAnimation(GSON.fromJson(new InputStreamReader(new GZIPInputStream(ResourceManager.getResource(source))), JsonObject.class));
	}

	private static void parseBBModel(String source, boolean gz) throws IOException {
		try (InputStreamReader reader = new InputStreamReader(ResourceManager.getResource(source))) {
			JsonObject parsed = BBModelParser.toCat(ResourceManager.readAllLines(reader));
			Path path = GENERATED_DIRECTORY.resolve(Utils.ifEndsWithOrAppend(source, gz ? ".json.gz" : ".json"));
			try (var writer = gz ?
					new OutputStreamWriter(new BufferedOutputStream(new GZIPOutputStream(Files.newOutputStream(path))))
					: Files.newBufferedWriter(path)) {
				GSON.toJson(parsed, writer);
			}
		}
	}

}