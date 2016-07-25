
class CEdge implements IEdge 
{
    
    int startNodeId;
    int endNodeId;
    double weight;
    
    
    public final int start()
    {
        return startNodeId;
    }
    
    public final int end()
    {
        return endNodeId;
    }
    
    public double weight()
    {
        return weight;
    }
    
}
