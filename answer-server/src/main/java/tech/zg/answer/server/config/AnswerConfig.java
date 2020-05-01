package tech.zg.answer.server.config;

public class AnswerConfig {

    /**
     * answer 服务的地址
     */
    private String answerAddress;
    /**
     * answer 服务的端口
     */
    private int answerAddressPort;
    /**
     * zookeeper 地址，服务注册使用
     */
    private String registryAddress;

    /**
     * 提供服务的包路径
     */
    private String servicePackage;

    public String getAnswerAddress() {
        return answerAddress;
    }

    public void setAnswerAddress(String answerAddress) {
        this.answerAddress = answerAddress;
    }

    public int getAnswerAddressPort() {
        return answerAddressPort;
    }

    public void setAnswerAddressPort(int answerAddressPort) {
        this.answerAddressPort = answerAddressPort;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }
}