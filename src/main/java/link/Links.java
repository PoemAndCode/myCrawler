package link;  
/**  
 * 类名: Links  
 * 作者： pfliu
 * 日期: 2018年11月19日
 * 时间：下午2:33:29  
 * 描述:存储已经访问过的URL路径 和 待访问的URL 路径;              
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Links
{
    //已访问的 url 集合  已经访问过的 主要考虑 不能再重复了 使用set来保证不重复;
    private static Set visitedUrlSet=new HashSet<>();
    //已访问的 url 集合  已经访问过的 主要考虑 不能再重复了 使用set来保证不重复;
    private static LinkedList unVisitedUrlQueue=new LinkedList<>();
    
    //获得已经访问的 URL 数目
    public static int getVisitedUrlNum()
    {
       return visitedUrlSet.size(); 
    }
    //添加到访问过的 URL
    public static void addVisitedUrlSet(String url) {
        visitedUrlSet.add(url);
    }
    //移除访问过的 URL
    public static void removeVisitedUrlSet(String url) {
        visitedUrlSet.remove(url);
    }
    
    //获得 待访问的 url 集合
    public static LinkedList getUnVisitedUrlQueue() {
        return unVisitedUrlQueue;
    }
 
    // 添加到待访问的集合中  保证每个 URL 只被访问一次
    public static void addUnvisitedUrlQueue(String url) {
        if (url != null && !url.trim().equals("")  && !visitedUrlSet.contains(url)  && !unVisitedUrlQueue.contains(url)){
            unVisitedUrlQueue.add(url);
        }
    }
 
    //删除 待访问的url
    public static Object removeHeadOfUnVisitedUrlQueue() {
        return unVisitedUrlQueue.removeFirst();
    }
 
    //判断未访问的 URL 队列中是否为空
    public static boolean unVisitedUrlQueueIsEmpty() {
        return unVisitedUrlQueue.isEmpty();
    }
}
  
