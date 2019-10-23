package cheng.community.dto;

import cheng.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng
 * @date 2019/10/8 0008 上午 11:35
 */
@Data
public class PaginationDTO<T> {

private List<T>data;
private boolean showPrevious;
private boolean showFirstPage;
private boolean showNext;
private boolean showEndPage;
private Integer totalPage;
private Integer page;
private List<Integer> pages=new ArrayList<>();


    public void setPagination(Integer totalPage, Integer page) {
        this.page=page;

        this.totalPage=totalPage;
        pages.add(page);
        for (int i = 1; i <=3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }
            if (page + i <=totalPage) {
                pages.add(page + i);
            }
        }
      //是否展示第一页
      if (page==1){
          showPrevious=false;
      }else{
          showPrevious=true;
      }
      //是否展示后一页
        if (page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否需要展示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        if (pages.contains(totalPage)) {
            showEndPage=false;
        }else{
            showEndPage=true;
        }
        }

    }

