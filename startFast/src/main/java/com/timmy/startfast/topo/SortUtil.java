package com.timmy.startfast.topo;

import com.timmy.startfast.TLog;
import com.timmy.startfast.task.AppStartTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 拓扑排序方法
 */
public class SortUtil {

    /**
     * 1。理解题意
     * -输入所有的任务，每个任务都知道当前任务的前序任务有那些，根据优先级，先执行入度为0的任务，然后返回拓扑排序好的任务集合
     * 2。解题思路
     * 2。1。数据准备
     * -根据节点的上层节点，
     * --获取节点的入，并将入度为0的任务，先放到队列中进行遍历  -- 任务与入度的关系表示
     * --根据上层节点到本节点的关系，获取上层节点，到下层节点的集合 -- 当前任务与下层任务集合关系
     * 2。2。然后根据入度为0，遍历下层节点，并切下层节点的入度不断减少，当入度减少为0时，又将当前节点添加到队列中
     * -不断取出队列中的任务执行，然后下层节点的入度减少，直到没有下层节点
     *
     * @param allExecutorTask
     */
    public static List<? extends AppStartTask> topoSort(List<? extends AppStartTask> allExecutorTask,
                                                        Map<Class<? extends AppStartTask>, AppStartTask> classTaskMap,
                                                        Map<Class<? extends AppStartTask>, List<Class<? extends AppStartTask>>> adj) {
        List<AppStartTask> sortRes = new ArrayList<>();

        // Class 与 AppStartTask的关系
//        Map<Class<? extends AppStartTask>, AppStartTask> classTaskMap = new HashMap<>();
        //入度
        Map<Class<? extends AppStartTask>, TopoSort> inDegreeMap = new HashMap<>();
        //临街表
//        Map<Class<? extends AppStartTask>, List<Class<? extends AppStartTask>>> adj = new HashMap<>();
        Queue<Class<? extends AppStartTask>> queue = new LinkedList<>();

        for (AppStartTask appStartTask : allExecutorTask) {
            classTaskMap.put(appStartTask.getClass(), appStartTask);
            adj.put(appStartTask.getClass(), new ArrayList<>());
            List<Class<? extends AppStartTask>> depends = appStartTask.dependsOn();
            inDegreeMap.put(appStartTask.getClass(), new TopoSort(depends == null ? 0 : depends.size()));

            if (inDegreeMap.get(appStartTask.getClass()).getIn() == 0) {
                queue.add(appStartTask.getClass());
            }
        }

        for (AppStartTask appStartTask : allExecutorTask) {
            List<Class<? extends AppStartTask>> depends = appStartTask.dependsOn();
            if (depends != null) {
                for (Class<? extends AppStartTask> depend : depends) {
                    // 上层节点到本节点任务的连接表结构
                    adj.get(depend).add(appStartTask.getClass());
                }
            }
        }

        while (!queue.isEmpty()) {
            Class<? extends AppStartTask> poll = queue.poll();
            sortRes.add(classTaskMap.get(poll));
            //临街表
            List<Class<? extends AppStartTask>> linkList = adj.get(poll);
            //获取当前节点下一层节点的集合，并将下一层节点的入度减1
            for (Class<? extends AppStartTask> linkNode : linkList) {
                //下层节点的入度减少
                TopoSort topoSort = inDegreeMap.get(linkNode);
                int newIn = topoSort.getIn() - 1;
                topoSort.setIn(newIn);
                inDegreeMap.put(linkNode, topoSort);
                if (newIn == 0) {
                    queue.offer(linkNode);
                }
            }
        }

        for (AppStartTask sortRe : sortRes) {
            TLog.d("sort:" + sortRe.getClass().getSimpleName());
        }
        return sortRes;
    }
}
