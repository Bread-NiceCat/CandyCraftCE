package cn.breadnicecat.candycraftce.utils;

/**
 * Created in 2023/8/26 0:07
 * Project: candycraftce
 *
 * @author <a href="https://github.com/Bread-Nicecat">Bread_NiceCat</a>
 */
public class MthUtils {
	/**
	 * 线性插值
	 * p1------p-------p2
	 * v1------v-------v2
	 *
	 * @param p  插值点坐标
	 * @param p1 顶点坐标1
	 * @param p2 顶点坐标2
	 * @param v1 顶点数值1
	 * @param v2 顶点数值2
	 * @return 插值后的数值
	 */
	public static float linearInterpolation(float p, float p1, float p2, float v1, float v2) {
		if (Float.compare(v1, v2) == 0 || Float.compare(p1, p2) == 0) {
			//value值相等 距离为0 不进行插值计算
			return v1;
		} else {
			return ((p2 - p) / (p2 - p1) * v1) + ((p - p1) / (p2 - p1) * v2);
		}
	}
}
