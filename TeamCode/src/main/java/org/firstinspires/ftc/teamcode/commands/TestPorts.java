package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class TestPorts extends CommandBase {
    Drive m_drive;
    String m_port;

    public TestPorts(Drive drive, String port) {
        m_drive = drive;
        m_port = port;

        addRequirements(m_drive);
    }

    @Override
    public void execute() {
        switch (m_port) {
            case "chm0":
                m_drive.setFL(1);
                break;
            case "chm1":
                m_drive.setFR(1);
                break;
            case "chm2":
                m_drive.setBR(1);
                break;
            case "chm3":
                m_drive.setBL(1);
                break;
            case "none":
                m_drive.setBL(0);
                m_drive.setBR(0);
                m_drive.setFL(0);
                m_drive.setFR(0);
                break;
        }
    }
}
