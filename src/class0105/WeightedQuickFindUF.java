package class0105;
/**
 * @Description 1.5.11
 *          权重快找算法，无法带来性能提升
 * @author Leon
 * @date 2016-07-15 16:34:41
 */
public class WeightedQuickFindUF {
    
    private int[] id;
    private int[] sz;
    private int count;
    
    public WeightedQuickFindUF(int N) {
        id = new int[N];
        sz = new int[N];
        count = N;
        
        for(int i=0; i<N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }
    
    public void weightedQuickFindUF(int p, int q) {
        int pID = find(p);
        int qID = find(q);
        if (pID == qID) return;
        /*
         *  原始quickfind直接将p组节点变为qID
         *  加权后变换方向根据组下节点个数，会有优化吗？
         *  不行，对权重的维护代价超出权重带来的操作跳过
         */
        
        if(sz[p] < sz[q]) 
            /* q组数量多，将p组变为q */
            for(int i = 0; i < id.length; i++) {
                if(id[i]==pID) {
                    id[i] = qID;
                    
//                    sz[q]++;
//                    sz[i] = sz[q];
                }
            }
        else
            for(int i = 0; i < id.length; i++) {
                if(id[i]==qID) {
                    id[i] = pID;
                }
            }
        count--;
    }
    
    public int find(int p) {
        return id[p];
    }
    
    public int getCount() {
        return count;
    }
    
}
