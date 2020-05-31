package connected.car.admin;

public class Pagination {
	private int curPage; //현재페이지
	private int startPage; //시작페이지
	private int endPage; //끝페이지
	private int total; //게시글 총 갯수
	private int cntPerPage; // 페이지당 글 갯수.
	private int lastPage; //마지막페이지
	private int start; //쿼리에 사용될 start
	private int end; //쿼리에 사용될 start
	private String keyword; //검색 시 사용될 keyword
	
	private int cntPage = 5;
	
	public Pagination() {
		
	}
	
	public Pagination(int total, int curPage, int cntPerPage) {
		this.curPage = curPage;
		this.total = total;
		this.cntPerPage = cntPerPage;
		calcLastPage(getTotal(), getCntPerPage());
		calcStartEndPage(getCurPage(), cntPage);
		calcStartEnd(getCurPage(), getCntPerPage());
	}
	
	
	
	public Pagination(int total, int curPage, int cntPerPage, String keyword) {
		super();
		this.curPage = curPage;
		this.total = total;
		this.cntPerPage = cntPerPage;
		this.keyword = keyword;
		calcLastPage(getTotal(), getCntPerPage());
		calcStartEndPage(getCurPage(), cntPage);
		calcStartEnd(getCurPage(), getCntPerPage());
	}

	//가장 마지막 페이지 계산
	public void calcLastPage(int total, int cntPerPage) {
		setLastPage((int)Math.ceil((double)total/(double)cntPerPage));
	}
	//시작, 끝 페이지 계싼
	public void calcStartEndPage(int curPage, int cntPage) {
		setEndPage(((int)Math.ceil((double)curPage / (double)cntPage)) * cntPage);
		if (getLastPage() < getEndPage()) {
			setEndPage(getLastPage());
		}
		setStartPage(getEndPage() - cntPage + 1);
		if (getStartPage() < 1) {
			setStartPage(1);
		}
	}
	
	// DB 쿼리에서 사용할 start, end값 계산
	public void calcStartEnd(int curPage, int cntPerPage) {
		setEnd(curPage * cntPerPage);
		setStart(getEnd() - cntPerPage + 1);
	}
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCntPerPage() {
		return cntPerPage;
	}
	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getCntPage() {
		return cntPage;
	}
	public void setCntPage(int cntPage) {
		this.cntPage = cntPage;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Pagination [curPage=" + curPage + ", startPage=" + startPage + ", endPage=" + endPage + ", total="
				+ total + ", cntPerPage=" + cntPerPage + ", lastPage=" + lastPage + ", start=" + start + ", end=" + end
				+ ", keyword=" + keyword + ", cntPage=" + cntPage + "]";
	}
	
}
