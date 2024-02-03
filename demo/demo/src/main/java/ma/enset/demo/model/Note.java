package ma.enset.demo.model;

public class Note {
    private String chap;
    private String text;
    private int pagenumber;
    private String AudioBookName;
    public Note( String audioBookName,String chap, String text, int pagenumber) {
        super();
        this.chap = chap;
        this.text = text;
        this.pagenumber = pagenumber;
        AudioBookName = audioBookName;
    }
    public String getChap() {
        return chap;
    }
    public String getText() {
        return text;
    }
    public int getPagenumber() {
        return pagenumber;
    }
    public String getAudioBookName() {
        return AudioBookName;
    }
    public void setChap(String chap) {
        this.chap = chap;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }
    public void setAudioBookName(String audioBookName) {
        AudioBookName = audioBookName;
    }
    @Override
    public String toString() {
        return "Note [chap=" + chap + ", text=" + text + ", pagenumber=" + pagenumber + ", AudioBookName=" + AudioBookName
                + "]";
    }

}
