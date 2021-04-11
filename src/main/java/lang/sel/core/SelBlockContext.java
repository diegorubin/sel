package lang.sel.core;

public class SelBlockContext {
    private Keyword blockType;
    private Integer position;

    public SelBlockContext(final Keyword blockType, final Integer position) {
        this.blockType = blockType;
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public Keyword getBlockType() {
        return blockType;
    }
}
