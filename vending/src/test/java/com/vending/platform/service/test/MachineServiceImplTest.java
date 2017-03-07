package com.vending.platform.service.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vending.platform.dao.IMachineDAO;
import com.vending.platform.domain.MachineOperater;
import com.vending.platform.domain.UserInfo;
import com.vending.platform.service.MachineService;

//指定bean注入的配置文件
@ContextConfiguration("classpath:spring-mybatis.xml")
// 使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
@RunWith(SpringJUnit4ClassRunner.class)
public class MachineServiceImplTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MachineService machineService;
    @Autowired
    private IMachineDAO machineDAO;
    private UserInfo userInfo;
    private MachineOperater machineOperater;

    @Before
    public void init() {
        userInfo = new UserInfo();
        machineOperater = new MachineOperater();

        userInfo.setUserId(2);
        userInfo.setUserNo("00101");
        userInfo.setUserName("厂商user1");
        userInfo.setRoleId(2);
        userInfo.setStatus(1);
        userInfo.setFirmId(2);
        userInfo.setParentUserId(1);

        machineOperater.setMachineAssign(0);
        machineOperater.setMachineName("售货机名牌1");
        machineOperater.setMachinePannel("售货机主板1");
    }

    /***************** 有bug *****************************/
    @Test
    public void tesGetMachine() {
        List<MachineOperater> maOperaters = (List<MachineOperater>) machineDAO
                .getAllMachine(userInfo, machineOperater);
        System.out.println("-----------------------------------------------");
        System.out.println(maOperaters.size());
        for (int i = 0; i < maOperaters.size(); i++) {
            System.out.println("售货机：" + maOperaters.get(i).toString());
            System.out.println(
                    "售货机管理 :" + maOperaters.get(i).getMachineInfo().toString());
        }
    }

    @Test
    public void getById() {
        machineOperater.setOperateId(1);
        MachineOperater mOperater = machineService
                .getMachineOperaterById(machineOperater.getOperateId());
        System.out.println("按Id查询：" + mOperater.toString());

    }

    @Test
    public void changeStatus() {
        machineOperater.setmOperaterId(1);
        machineService.changeMachineStatus(0, machineOperater.getmOperaterId());
    }

    @Test
    public void uodateGroup() {
        Integer groupId = 2;
        machineOperater.setmOperaterId(1);
        machineDAO.updateMachineGroup(groupId,
                machineOperater.getmOperaterId());
    }
}
