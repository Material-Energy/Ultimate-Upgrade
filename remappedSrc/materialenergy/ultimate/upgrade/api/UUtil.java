package materialenergy.ultimate.upgrade.common.api;

import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class UUtil {
    public static boolean compareVector(Vec3d vec1, Vec3d vec2, boolean lessThan){
        boolean x = lessThan ? vec1.x < vec2.x: vec1.x > vec2.x;
        boolean y = lessThan ? vec1.y < vec2.y: vec1.y > vec2.y;
        boolean z = lessThan ? vec1.z < vec2.z: vec1.z > vec2.z;
        return x && y && z;
    }

    public static LiteralText getSymbol(int level) {
        LiteralText text = new LiteralText("");
        switch (level){
            case 1 -> text.append("β ").formatted(Formatting.GRAY);
            case 2 -> text.append("Δ ").formatted(Formatting.GRAY);
            case 3 -> text.append("θ ").formatted(Formatting.GRAY);
            case 4 -> text.append("ψ ").formatted(Formatting.GRAY);
            case 5 -> text.append("Ω ").formatted(Formatting.GOLD);
        }
        return text;
    }
}
