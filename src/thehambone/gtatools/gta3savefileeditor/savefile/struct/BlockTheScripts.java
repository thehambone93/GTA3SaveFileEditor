package thehambone.gtatools.gta3savefileeditor.savefile.struct;

import thehambone.gtatools.gta3savefileeditor.util.DataBuffer;
import thehambone.gtatools.gta3savefileeditor.savefile.SaveFile;
import thehambone.gtatools.gta3savefileeditor.savefile.UnsupportedPlatformException;
import thehambone.gtatools.gta3savefileeditor.savefile.var.VarArray;
import thehambone.gtatools.gta3savefileeditor.savefile.var.VarDWORD;
import thehambone.gtatools.gta3savefileeditor.savefile.var.VarInt;

/**
 * Created on Sep 21, 2015.
 * 
 * @author thehambone
 */
public class BlockTheScripts extends Block
{
    public final VarInt nScriptVariableSpaceSize = new VarInt();
    public final VarArray<VarDWORD> aScriptVariable
            = new VarArray<>(VarDWORD.class, 0);
    public final BlockSCMData scmState = new BlockSCMData(0x00);
    public final VarInt nNumberOfRunningScripts = new VarInt();
    public final VarArray<RunningScript> aRunningScript
            = new VarArray<>(RunningScript.class, 0);
    
    public BlockTheScripts(int size)
    {
        super("TheScripts", size);
    }
    
    @Override
    public void setSize(int size)
    {
        this.size = size;
    }
    
    @Override
    public DataStructure[] getMembers()
    {
        return new DataStructure[] {
            nScriptVariableSpaceSize, aScriptVariable, scmState,
            nNumberOfRunningScripts, aRunningScript
        };
    }
    
    @Override
    protected void loadAndroid(DataBuffer buf)
    {
        nScriptVariableSpaceSize.load(buf, offset + 0x08);
        
        aScriptVariable.setElementCount(nScriptVariableSpaceSize.getValue()
                / aScriptVariable.getSizeOfElement());
        aScriptVariable.load(buf, offset + 0x0C, SaveFile.Platform.ANDROID);
        
        buf.seek(offset + nScriptVariableSpaceSize.getValue() + 0x0C);
        scmState.setSize(buf.readInt());
        scmState.load(buf, offset + nScriptVariableSpaceSize.getValue() + 0x10,
                SaveFile.Platform.ANDROID);
        
        nNumberOfRunningScripts.load(buf,
                offset + nScriptVariableSpaceSize.getValue()
                        + scmState.getSize() + 0x10);
        
        aRunningScript.setElementCount(nNumberOfRunningScripts.getValue());
        aRunningScript.load(buf,
                offset + nScriptVariableSpaceSize.getValue()+ scmState.getSize()
                        + 0x14, SaveFile.Platform.ANDROID);
    }
    
    @Override
    protected void loadIOS(DataBuffer buf)
    {
        nScriptVariableSpaceSize.load(buf, offset + 0x08);
        
        aScriptVariable.setElementCount(nScriptVariableSpaceSize.getValue()
                / aScriptVariable.getSizeOfElement());
        aScriptVariable.load(buf, offset + 0x0C, SaveFile.Platform.IOS);
        
        buf.seek(offset + nScriptVariableSpaceSize.getValue() + 0x0C);
        scmState.setSize(buf.readInt());
        scmState.load(buf, offset + nScriptVariableSpaceSize.getValue() + 0x10,
                SaveFile.Platform.IOS);
        
        nNumberOfRunningScripts.load(buf,
                offset + nScriptVariableSpaceSize.getValue()
                        + scmState.getSize() + 0x10);
        
        aRunningScript.setElementCount(nNumberOfRunningScripts.getValue());
        aRunningScript.load(buf,
                offset + nScriptVariableSpaceSize.getValue()+ scmState.getSize()
                        + 0x14, SaveFile.Platform.IOS);
    }
    
    @Override
    protected void loadPC(DataBuffer buf)
    {
        nScriptVariableSpaceSize.load(buf, offset + 0x08);
        
        aScriptVariable.setElementCount(nScriptVariableSpaceSize.getValue()
                / aScriptVariable.getSizeOfElement());
        aScriptVariable.load(buf, offset + 0x0C, SaveFile.Platform.PC);
        
        buf.seek(offset + nScriptVariableSpaceSize.getValue() + 0x0C);
        scmState.setSize(buf.readInt());
        scmState.load(buf, offset + nScriptVariableSpaceSize.getValue() + 0x10,
                SaveFile.Platform.PC);
        
        nNumberOfRunningScripts.load(buf,
                offset + nScriptVariableSpaceSize.getValue()
                        + scmState.getSize() + 0x10);
        
        aRunningScript.setElementCount(nNumberOfRunningScripts.getValue());
        aRunningScript.load(buf,
                offset + nScriptVariableSpaceSize.getValue()+ scmState.getSize()
                        + 0x14, SaveFile.Platform.PC);
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
