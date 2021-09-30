package com.lyricchan.arknights.penguinstatsclient.entity;

public class MatrixRequest implements java.io.Serializable {

    /**
     * <p>server</p>
     * <p>说明欲请求的服务器代码。目前我们支持 4 个服务器："CN", "US", "JP" 和 "KR"。默认值为 "CN"。</p>
     */
    private String server = "CN";

    /**
     * <p>show_closed_zones</p>
     * <p>是否显示已经关闭的 Zone 与 Stage。 默认值为 false。</p>
     */
    private Boolean show_closed_zones;

    /**
     * <p>is_personal</p>
     * <p>使用全平台数据或个人数据。若提供了 true，请确保 Cookie 中有对应的 userID。 默认值为 false。</p>
     */
    private Boolean is_personal;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Boolean getShow_closed_zones() {
        return show_closed_zones;
    }

    public void setShow_closed_zones(Boolean show_closed_zones) {
        this.show_closed_zones = show_closed_zones;
    }

    public Boolean getIs_personal() {
        return is_personal;
    }

    public void setIs_personal(Boolean is_personal) {
        this.is_personal = is_personal;
    }
}
