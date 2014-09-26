package java.com.magicbox.redio.blocks;

public class BUS extends BaseBlock
{
	public BUS(Material material)
	{
		super(material);
		setHardness(1.0f);
		setBlockName(Constants.ScriptStorage.BLOCK_NAME);
		setCreativeTab(Instances.creativeTab);
		setHarvestLevel("pickaxe", 1);
		setBlockTextureName(Constants.BUS.TEXTURE_NAME);
	}
}
