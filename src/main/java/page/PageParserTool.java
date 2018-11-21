package page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**  
 * 类名: PageParserTool  
 * 作者： pfliu
 * 日期: 2018年11月19日
 * 时间：下午2:07:16                
 */
public class PageParserTool
{
    
    /**  
     * select:通过选择器选取页面.   
     * @author pfliu  
     * @param page
     * @param cssSelector
     * @return  
     */
    public static Elements  select(Page page,String cssSelector)
    {
        return page.getDoc().select(cssSelector);
    }
    
    /**  
     * select:通过css选择器得到指定元素.   
     * @author pfliu  
     * @param page
     * @param cssSelector
     * @param index
     * @return  
     */
    public static Element  select(Page page,String cssSelector,int index)
    {
        Elements elements=select(page, cssSelector);
        int realIndex=index;
        if(index<0)
            realIndex=elements.size()+index;
        return elements.get(realIndex);
    }
    
    /**  
     * getLinks: 获取满足选择器的元素中的链接 选择器cssSelector必须定位到具体的超链接
     * 例如我们想抽取id为content的div中的所有超链接，这里
     * 就要将cssSelector定义为div[id=content] a
     *  放入set 中 防止重复.   
     * @author pfliu  
     * @param page
     * @param cssSelector
     * @return  
     */
    public static Set<String > getLinks(Page page,String cssSelector)
    {
        Set<String> links=new HashSet<>();
        Elements es =select(page, cssSelector);
        Iterator it=es.iterator();
        while (it.hasNext())
        {
            Element element = (Element) it.next();
            if(element.hasAttr("href")) {
                links.add(element.attr("abs:href"));
            }else if(element.hasAttr("src")){
                links.add(element.attr("abs:src"));
            }
        }
        return links;
     }
    /**  
     * getAttrs:获取网页中满足指定css选择器的所有元素的指定属性的集合
     * 例如通过getAttrs("img[src]","abs:src")可获取网页中所有图片的链接   
     * @author pfliu  
     * @param page
     * @param cssSelector
     * @param attrName
     * @return  
     */
    public static ArrayList<String> getAttrs(Page page,String cssSelector,String attrName)
    {
        ArrayList<String> result=new ArrayList<>();
        Elements sles =select(page, cssSelector);
        for (Element element : sles)
        {
            if(element.hasAttr(attrName)) {
                result.add(element.attr(attrName));
            }
        }
        return result;
    }
}
  
