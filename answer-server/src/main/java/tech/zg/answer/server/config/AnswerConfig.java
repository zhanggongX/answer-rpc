package tech.zg.answer.server.config;

public class AnswerConfig {

    /**
     * answer 服务的端口
     */
    private int answerPort;
    /**
     * zookeeper 地址，服务注册使用
     */
    private String registryAddress;

    /**
     * 提供服务的包路径
     */
    private String servicePackage;

    public int getAnswerPort() {
        return answerPort;
    }

    public void setAnswerPort(int answerPort) {
        this.answerPort = answerPort;
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