-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema reportpool
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema reportpool
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `reportpool` DEFAULT CHARACTER SET utf8 ;
USE `reportpool` ;

-- -----------------------------------------------------
-- Table `reportpool`.`attachmenttype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`attachmenttype` (
  `AttachmenttypeID` INT(11) NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`AttachmenttypeID`),
  UNIQUE INDEX `AttachmenttypeID_UNIQUE` (`AttachmenttypeID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`department` (
  `DepartmentID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`DepartmentID`),
  UNIQUE INDEX `DepartmentID_UNIQUE` (`DepartmentID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`unit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`unit` (
  `UnitID` INT(11) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`UnitID`),
  UNIQUE INDEX `UnitID_UNIQUE` (`UnitID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`departmentunit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`departmentunit` (
  `DepartmentUnitID` INT(11) NOT NULL AUTO_INCREMENT,
  `department_id` INT(11) NOT NULL,
  `unit_id` INT(11) NOT NULL,
  PRIMARY KEY (`DepartmentUnitID`),
  UNIQUE INDEX `DepartmentUnitID_UNIQUE` (`DepartmentUnitID` ASC),
  INDEX `fk_departmentunit_department1_idx` (`department_id` ASC),
  INDEX `fk_departmentunit_unit1_idx` (`unit_id` ASC),
  CONSTRAINT `fk_departmentunit_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `reportpool`.`department` (`DepartmentID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_departmentunit_unit1`
    FOREIGN KEY (`unit_id`)
    REFERENCES `reportpool`.`unit` (`UnitID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`user` (
  `UserID` INT(11) NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(255) NOT NULL,
  `Password` TINYTEXT NULL DEFAULT NULL,
  `IsActive` TINYINT(1) NULL DEFAULT '0',
  `Name` VARCHAR(255) NOT NULL,
  `Surname` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `UserID_UNIQUE` (`UserID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`report` (
  `ReportID` INT(11) NOT NULL AUTO_INCREMENT,
  `Title` LONGTEXT NULL DEFAULT NULL,
  `Text` LONGTEXT NULL DEFAULT NULL,
  `IsCompleted` TINYINT(4) NULL DEFAULT NULL,
  `DateCompleted` DATE NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  `departmentunit_id` INT(11) NOT NULL,
  PRIMARY KEY (`ReportID`),
  INDEX `fk_report_user1_idx` (`user_id` ASC),
  INDEX `fk_report_departmentunit1_idx` (`departmentunit_id` ASC),
  CONSTRAINT `fk_report_departmentunit1`
    FOREIGN KEY (`departmentunit_id`)
    REFERENCES `reportpool`.`departmentunit` (`DepartmentUnitID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_report_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `reportpool`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`attachment` (
  `AttachmentID` INT(11) NOT NULL AUTO_INCREMENT,
  `Path` VARCHAR(255) NULL DEFAULT NULL,
  `attachmenttype_id` INT(11) NOT NULL,
  `report_id` INT(11) NOT NULL,
  PRIMARY KEY (`AttachmentID`),
  UNIQUE INDEX `AttachmentID_UNIQUE` (`AttachmentID` ASC),
  INDEX `fk_attachment_attachmenttype1_idx` (`attachmenttype_id` ASC),
  INDEX `fk_attachment_report1_idx` (`report_id` ASC),
  CONSTRAINT `fk_attachment_attachmenttype1`
    FOREIGN KEY (`attachmenttype_id`)
    REFERENCES `reportpool`.`attachmenttype` (`AttachmenttypeID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_attachment_report1`
    FOREIGN KEY (`report_id`)
    REFERENCES `reportpool`.`report` (`ReportID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`role` (
  `RoleID` INT(11) NOT NULL AUTO_INCREMENT,
  `Rolename` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`RoleID`),
  UNIQUE INDEX `RoleID_UNIQUE` (`RoleID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`userroles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`userroles` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_UserRoles_User_idx` (`user_id` ASC),
  INDEX `fk_UserRoles_Role1_idx` (`role_id` ASC),
  CONSTRAINT `fk_UserRoles_Role1`
    FOREIGN KEY (`role_id`)
    REFERENCES `reportpool`.`role` (`RoleID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserRoles_User`
    FOREIGN KEY (`user_id`)
    REFERENCES `reportpool`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `reportpool`.`userunit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reportpool`.`userunit` (
  `unit_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`unit_id`, `user_id`),
  INDEX `fk_unit_has_user_user1_idx` (`user_id` ASC),
  INDEX `fk_unit_has_user_unit1_idx` (`unit_id` ASC),
  CONSTRAINT `fk_unit_has_user_unit1`
    FOREIGN KEY (`unit_id`)
    REFERENCES `reportpool`.`unit` (`UnitID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_unit_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `reportpool`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
