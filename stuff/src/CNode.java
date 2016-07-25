
class CNode implements INode 
{
    
    int nodeId;
    int gridPosX;
    int gridPosY;
    
    
    public final int id()
    {
        return nodeId;
    }
    
    public final int xPos()
    {
        return gridPosX;
    }
    
    public final int yPos()
    {
        return gridPosY;
    }
    
}
