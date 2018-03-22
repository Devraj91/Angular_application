/*create database einvoice;*/
/*create database crm;*/


/*Category fee master data*/
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(1,'A1',750000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(2,'A2',1250000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(3,'A3',2500000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(4,'A4',4000000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(5,'A5',6000000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(6,'B',450000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(7,'C',200000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(8,'D',85000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(9,'E',50000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(10,'F',25000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(11,'S',5000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(12,'T',10000);
INSERT INTO `einvoice`.`category_fee`(`id`,`category`,`fee`)VALUES(13,'X',55000);


/*City master data*/
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (1,'Bengaluru');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (2,'Hosur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (3,'Hubli');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (4,'Mangalore');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (5,'Mysore');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (6,'Tumakuru');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (7,'Udipi');

INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (8,'Chennai');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (9,'Coimbatore');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (10,'Dindigul');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (11,'Erode');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (12,'Kanchipuram');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (13,'Kangayam');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (14,'Karur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (15,'Madurai');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (16,'Nagercoil');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (17,'Pondicherry');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (18,'Rasipuram');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (19,'Siruseri');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (20,'Sivakasi');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (21,'Sriperumbudur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (22,'Thanjavur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (23,'Tiruchchirappalli');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (24,'Tirunelveli');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (25,'Trichy');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (26,'Vellore');

INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (27,'Guntur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (28,'Hyderabad');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (29,'Rajahmundry');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (30,'Secunderabad');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (31,'Tirupati');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (32,'Vijayawada');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (33,'Visakhapatnam');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (34,'Warangal');

INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (35,'Alappuzha');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (36,'Calicut');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (37,'Cochin');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (38,'Ernakulam');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (39,'Kannur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (40,'Kerala');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (41,'Kochi');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (42,'Kollam');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (43,'Kozhikode');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (44,'Palakkad');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (45,'Thiruvananthapuram');

INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (46,'Agartala');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (47,'Bhubaneswar');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (48,'Cuttack');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (49,'Durgapur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (50,'Guwahati');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (51,'Jamshedpur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (52,'Kharagpur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (53,'Kolkata');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (54,'Patna');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (55,'Ranchi');

INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (56,'Ahmedabad');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (57,'Anand');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (58,'Bhavnagar');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (59,'Bhilai');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (60,'Bhopal');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (61,'Gandhinagar');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (62,'Gwalior');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (63,'Indore');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (64,'Jabalpur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (65,'Mumbai');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (66,'Navi Mumbai');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (67,'Raipur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (68,'Rajkot');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (69,'Surat');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (70,'Thane');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (71,'Vadodara');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (72,'Vapi');


INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (73,'Ambala');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (74,'Bhatinda');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (75,'Chandigarh');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (76,'Dehra Dun');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (77,'Delhi');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (78,'Faridabad');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (79,'Ghaziabad');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (80,'Greater Noida');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (81,'Gurugram');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (82,'Jaipur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (83,'Jalandhar');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (84,'Kota');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (85,'Lucknow');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (86,'Ludhiana');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (87,'Mohali');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (88,'New Delhi');

INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (89,'Noida');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (90,'Panchkula');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (91,'Panipat');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (92,'Udaipur');

INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (93,'Akola');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (94,'Aurangabad');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (95,'Kolhapur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (96,'Margao');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (97,'Nagpur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (98,'Nasik');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (99,'Panaji');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (100,'Pune');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (101,'Satara');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (102,'Solapur');
INSERT INTO `einvoice`.`city`(`city_id`,`city_name`) VALUES (103,'Gurgaon');

/*Branch Sender Detail Master*/
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (1, 'Noida Head', 'pankaj.bansal@globallogic.com','Nasscom North');
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (2, 'Bengaluru Head', 'pankaj.bansal@globallogic.com','Nasscom Bangalore');
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (3, 'Chennai Head', 'pankaj.bansal@globallogic.com','Nasscom Chennai');
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (4, 'Hyderabad Head', 'pankaj.bansal@globallogic.com','Nasscom Hyderabad');
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (5, 'Kerala Head', 'pankaj.bansal@globallogic.com','Nasscom Kochi');
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (6, 'Kolkata Head', 'pankaj.bansal@globallogic.com','Nasscom Kolkata');
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (7, 'Mumbai Head', 'pankaj.bansal@globallogic.com','Nasscom Mumbai');
INSERT INTO `einvoice`.`sender_detail`(`sender_id`,`designation`,`email`,`name`) VALUES (8, 'Pune Head', 'pankaj.bansal@globallogic.com','Nasscom Pune');


/*Branch details Master*/
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(1,'09AADCV3269N1ZO','North','','NASSCOM Plot 7 to 10, Sector 126,',201303,'','',89,1);
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(2,'06AAFCK1875R1ZV','Bengaluru','','JSS Institutions Campus, First Floor, CA Site No.1, HAL 3rd Stage Behind Hotel Leela Palace',560008,'','',1,2);
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(3,'09AADCV3269N1ZO','Chennai','','TRIL Info park Amenity Floor, Hardy Towers, OMR',600113,'','',8,3);
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(4,'09AADCV3269N1ZO','Hyderabad','','Unit 105, 1st Floor, Maximus 2B Raheja MindSpace, Madhapur',500082,'','',28,4);
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(5,'07AGAPR4504N1Z8','Kerala','','NASSCOM, 10,000 Startups Warehouse, Infopark Campus, Kakkanad',682030,'','',41,5);
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(6,'29AADCI1475R1ZV','Kolkata','','Module No: 414, 3rd floor, STP Complex, SDF Building,Sector-V, Bidhannagar',700091,'','',53,6);
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(7,'36AAACQ3062B1ZX','Mumbai','','Samruddhi Venture Park Ground Floor, Office # 14-15 Central MIDC Road, Andheri East',400093,'','',65,7);
INSERT INTO `einvoice`.`branch`(`branch_id`,`gst_no`,`name`,`pan`,`street`,`pin`,`phone`,`fax`,`city_id`,`sender_id`)VALUES(8,'08AALFC0387M1Z0','Pune','','B Wing, 5th Floor, MCCIA Trade Tower, International Convention Centre Complex, Senapati Bapat Road',411016,'','',100,8);

/*City to Region mapping master*/
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (1,1,1);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (2,2,1);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (3,3,1);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (4,4,1);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (5,5,1);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (6,6,1);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (7,7,1);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (8,8,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (9,9,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (10,10,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (11,11,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (12,12,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (13,13,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (14,14,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (15,15,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (16,16,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (17,17,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (18,18,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (19,19,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (20,20,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (21,21,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (22,22,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (23,23,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (24,24,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (25,25,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (26,26,2);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (27,27,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (28,28,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (29,29,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (30,30,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (31,31,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (32,32,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (33,33,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (34,34,3);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (35,35,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (36,36,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (37,37,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (38,38,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (39,39,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (40,40,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (41,41,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (42,42,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (43,43,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (44,44,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (45,45,4);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (46,46,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (47,47,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (48,48,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (49,49,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (50,50,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (51,51,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (52,52,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (53,53,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (54,54,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (55,55,5);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (56,56,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (57,57,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (58,58,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (59,59,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (60,60,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (61,61,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (62,62,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (63,63,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (64,64,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (65,65,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (66,66,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (67,67,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (68,68,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (69,69,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (70,70,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (71,71,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (72,72,6);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (73,73,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (74,74,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (75,75,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (76,76,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (77,77,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (78,78,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (79,79,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (80,80,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (81,81,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (82,82,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (83,83,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (84,84,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (85,85,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (86,86,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (87,87,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (88,88,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (89,89,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (90,90,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (91,91,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (92,92,7);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (93,93,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (94,94,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (95,95,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (96,96,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (97,97,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (98,98,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (99,99,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (100,100,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (101,101,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (102,102,8);
INSERT INTO `einvoice`.`city_region_mapping`(`city_region_mapping_id`,`city_id`,`branch_id`) VALUES (103,103,7);

/*Email Template Master*/
INSERT INTO `einvoice`.`email_template`(`email_template_id`,`body`,`designation`,`is_external`,`name`,`ps`,`subject`,`template_type`) VALUES (1, 'Its a reminder mail to you,Please Pay your invoice balance.', 'Head',0,'Reminder Template','From Nasscom Branch','Reminder Mail','Reminder');
INSERT INTO `einvoice`.`email_template`(`email_template_id`,`body`,`designation`,`is_external`,`name`,`ps`,`subject`,`template_type`) VALUES(2, 'Please find the attachment of latest Invoice',' Head',0, 'Invoice Template','From Nasscom Branch','Invoice Mail','Invoice');
INSERT INTO `einvoice`.`email_template` (`email_template_id`, `body`, `designation`, `is_external`, `name`, `ps`, `subject`, `template_type`) VALUES ('3', 'Please find the attachment of latest Receipt', 'Head', '0', 'Receipt Template', 'From Nasscom Branch', 'Receipt Mail', 'Receipt');


/*Tax Config Detail Master*/
INSERT INTO `einvoice`.`tax_config`(`taxconfig_id`,`create_time`,`created_by`,`modified_by`,`last_updated`,`rate`,`tax_applicable`,`tax_name`,`city_id`) VALUES (1,null,null,null,null, 9, 'true','SGST',88);
INSERT INTO `einvoice`.`tax_config`(`taxconfig_id`,`create_time`,`created_by`,`modified_by`,`last_updated`,`rate`,`tax_applicable`,`tax_name`,`city_id`) VALUES (2,null,null,null,null, 9, 'true','CGST',88);
INSERT INTO `einvoice`.`tax_config`(`taxconfig_id`,`create_time`,`created_by`,`modified_by`,`last_updated`,`rate`,`tax_applicable`,`tax_name`,`city_id`) VALUES (3,null,null,null,null, 18, 'true','IGST',88);

/*Role master data*/
INSERT INTO `einvoice`.`role`(`role_id`,`role_name`) VALUES (2,'Sub-Admin');
INSERT INTO `einvoice`.`role`(`role_id`,`role_name`) VALUES (3,'SuperAdmin');

/*User master data*/
UPDATE `einvoice`.`user` SET `branch_id`='1' WHERE `user_id`='1';

/* TDS Config Insert TDS rate*/
INSERT INTO `einvoice`.`tds_config` (`tdsconfig_id`, `rate`) VALUES ('1', '2');
INSERT INTO `einvoice`.`tds_config` (`tdsconfig_id`, `rate`) VALUES ('2', '10');

/* Mode of Payment */
INSERT INTO `einvoice`.`mode_of_payment` (`mode_of_payment_id`, `method`) VALUES ('1', 'Cheque/DD');
INSERT INTO `einvoice`.`mode_of_payment` (`mode_of_payment_id`, `method`) VALUES ('2', 'NEFT');
INSERT INTO `einvoice`.`mode_of_payment` (`mode_of_payment_id`, `method`) VALUES ('3', 'Credit Card');


