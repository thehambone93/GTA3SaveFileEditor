package thehambone.gtatools.gta3savefileeditor.savefile.struct;

import thehambone.gtatools.gta3savefileeditor.util.DataBuffer;
import thehambone.gtatools.gta3savefileeditor.savefile.UnsupportedPlatformException;
import thehambone.gtatools.gta3savefileeditor.savefile.var.VarInt;

/**
 * Created on Sep 20, 2015.
 * 
 * @author thehambone
 */
public class BuildingSwap extends Record
{
    public final VarInt nType = new VarInt();
    public final VarInt nHandle = new VarInt();
    public final VarInt nNewModel = new VarInt();
    public final VarInt nOldModel = new VarInt();
    
    public BuildingSwap()
    {
        super(0x10);
    }
    
    private void load(DataBuffer buf)
    {
        nType.load(buf, offset + 0x00);
        nHandle.load(buf, offset + 0x04);
        nNewModel.load(buf, offset + 0x08);
        nOldModel.load(buf, offset + 0x0C);
    }
    
    @Override
    public DataStructure[] getMembers()
    {
        return new VarInt[] {
            nType, nHandle, nNewModel, nOldModel
        };
    }
    
    @Override
    protected void loadAndroid(DataBuffer buf)
    {
        load(buf);
    }
    
    @Override
    protected void loadIOS(DataBuffer buf)
    {
        load(buf);
    }
    
    @Override
    protected void loadPC(DataBuffer buf)
    {
        load(buf);
    }
    
    @Override
    protected void loadPS2(DataBuffer buf)
    {
        throw new UnsupportedPlatformException("PS2 not supported yet.");
    }
    
    @Override
    protected void loadXbox(DataBuffer buf)
    {
        throw new UnsupportedPlatformException("Xbox not supported yet.");
    }
}
