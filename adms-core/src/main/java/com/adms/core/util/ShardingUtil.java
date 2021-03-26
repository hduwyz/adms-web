package com.adms.core.util;

/**
 * @author wangyz
 * @date 2021年03月25日 16:44
 */
public class ShardingUtil {
    private static InheritableThreadLocal<ShardingVO> contextHolder = new InheritableThreadLocal<>();

    public static class ShardingVO {

        private int index;  // sharding index
        private int total;  // sharding total

        public ShardingVO(int index, int total) {
            this.index = index;
            this.total = total;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static void setShardingVo(ShardingVO shardingVo) {
        contextHolder.set(shardingVo);
    }

    public static ShardingVO getShardingVo() {
        return contextHolder.get();
    }
}
