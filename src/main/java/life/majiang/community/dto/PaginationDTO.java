package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 */

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;//向前按钮
    private boolean showFirstPage;//最前按钮

    private boolean showNext;//向后按钮
    private boolean showEndPage;//最后按钮

    private Integer page;//页码数
    private List<Integer> pages = new ArrayList<>();//页码数组

    private Integer totalPage;
    /**
     * 分页显示
     *
     * @param totalCount 查询总数
     * @param page       页码数
     * @param size       每页显示
     */
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        //所有页数
        if (totalCount % size == 0) {//所有页数求余每页显示数量
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        this.page = page;
        //是否显示上一页按钮
        if (page == 1) {//第一页
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否显示下一页按钮
        if (page == totalPage) {//最后一页
            showNext = false;
        } else {
            showNext = true;
        }
        //是否显示最前
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否显示最后
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }

        //默认把1放进
        pages.add(page);

        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
    }
}
