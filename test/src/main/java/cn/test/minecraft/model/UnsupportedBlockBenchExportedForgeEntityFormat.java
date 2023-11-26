package cn.test.minecraft.model;

public class UnsupportedBlockBenchExportedForgeEntityFormat extends RuntimeException {

	public UnsupportedBlockBenchExportedForgeEntityFormat(String errorMsg, int lineAt, String line) {
		super("\n" + errorMsg + "(at line " + lineAt + ")\n\t" + line + "\n");
	}
}
