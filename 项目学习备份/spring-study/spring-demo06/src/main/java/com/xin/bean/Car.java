package com.xin.bean;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.bean
 * @Author: LI Renxin
 * @CreateTime: 2021-06-19 20:26
 * @ModificationHistory Who    When    What
 * @Description: 车
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String type;
    private String carName;
    private String name;
}
