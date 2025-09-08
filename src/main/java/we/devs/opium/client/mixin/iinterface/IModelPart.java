package we.devs.opium.client.mixin.iinterface;

import net.minecraft.client.model.ModelPart;

import java.util.Map;

public interface IModelPart {

    Map<String, ModelPart> pulse$getChildren();

}
