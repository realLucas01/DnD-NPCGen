INSERT INTO Race_Model (race_id,race_ability_score,race_age_adult,race_age_max,race_creature_type,race_features,race_height,race_languages,race_name,race_size,race_skills,race_speed) VALUES (1,'Dex+2, Wis+2',3,30,'Humanoid','Flight, Talons, WindCaller','5','Common, Aarakocra','Aarokcra','medium',NULL,'30w,30f'),
 (2,'Cha+2, Wis+1',18,160,'Humaoind','Darkvision, Celestial Resistance, Celestial Legacy','5-6.9','Common, Cesestial','Aasimar','small, medium',NULL,'30w'),
 (3,'Str+2, Dex+1',16,80,'Humanoid, Goblinoid','Darkvision, Fey Ancestry, Long-Limbed, Powerful Buil, Sneaky, Suprise Attack','6-8','Common, +1','Bugbear','medium','Stealth','30w'),
 (4,'Str+2, Wis+1',18,100,'Fey','Fey, Charge, Hooves, Equine Build, Survivor','6-8','Common, Sylvan','Centaur','medium','One: Animal Handling, Medicine, Nature, Survival','40w'),
 (5,'Str+2,Cha+1',15,80,'Humanoid','Draconic Ancestry, Breath Weapon, Damage Resistance','6-8','Common, Draconic','Dragenborn','medium',NULL,'30w');
 
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (1,'Talons','You have talons that you can use to make unarmed strikes. When you hit with them, the strike deals 1d6 + your Strength modifier slashing damage, instead of the bludgeoning damage normal for an unarmed strike.','natural attack');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (3,'Flight','Because of your wings, you have a flying speed equal to your walking speed. You can''t use this flying speed if you''re wearing medium or heavy armor.','movement speed');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (5,'Celestial Resistance','You have resistance to necrotic and radiant damage.','damage resistance');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (7,'Long-Limbed','When you make a melee attack on your turn, your reach for it is 5 feet greater than normal.','bonus text');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (8,'Powerful Build','You count as one size larger when determining your carrying capacity and the weight you can push, drag, or lift.','bonus text');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (9,'Sneaky','You are proficient in the Stealth skill.','skill: stealth');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (10,'Surprise Attack','If you surprise a creature and hit it with an attack on your first turn in combat, the attack deals an extra 2d6 damage to it. You can use this trait only once per combat.','bonus text');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (11,'Fey Ancestry','You have advantage on saving throws you make to avoid or end the charmed condition on yourself.','advantage');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (12,'Fey','Your creature type is fey, rather than humanoid.','bonus text');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (13,'Charge','If you move at least 30 feet straight toward a target and then hit it with a melee weapon attack on the same turn, you can immediately follow that attack with a bonus action, making one attack against the target with your hooves.','bonus text');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (14,'Hooves','Your hooves are natural melee weapons, which you can use to make unarmed strikes. If you hit with them, you deal bludgeoning damage equal to 1d4 + your Strength modifier, instead of the bludgeoning damage normal for an unarmed strike.','natural attack');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (16,'Survivor','You have proficiency in one of the following skills of your choice: Animal Handling, Medicine, Nature, or Survival.','skill: choose: animal handling, medicine, nature, survival');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (17,'Draconic Ancestry','You have draconic ancestry. Choose one type of dragon from the Draconic Ancestry table. Your breath weapon and damage resistance are determined by the dragon type, as shown in the table.','bonus table: draconic weapon');
INSERT INTO feature_model(Feature_ID,Feature_Name,Feature_Description,Feature_Kind_Of) VALUES (19,'Damage Resistance','You have resistance to the damage type associated with your draconic ancestry.','damage resistance');
 
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (1,'Black','Acid','5 by 30 ft. line (Dex. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (2,'Blue','Lightning','5 by 30 ft. line (Dex. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (3,'Brass','Fire','5 by 30 ft. line (Dex. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (4,'Bronze','Lightning','5 by 30 ft. line (Dex. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (5,'Copper','Acid','5 by 30 ft. line (Dex. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (6,'Gold','Fire','15 ft. cone (Dex. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (7,'Green','Poison','15 ft. cone (Con. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (8,'Red','Fire','15 ft. cone (Dex. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (9,'Silver','Cold','15 ft. cone (Con. save)');
INSERT INTO Drac_Ances_Model(draconic_id,draconic_Dragon,draconic_Damage_Type,draconic_Breath_Weapon) VALUES (10,'White','Cold','15 ft. cone (Con. save)');

INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (1,'Artificer','d8','simple Weapon, firearms','light armor, medium armor, shields','Con, Int','Arcana, History, Investigation, Medicine, Nature, Perception, Sleight of Hand',2,'Int, Con, Dex, Wis, Str, Cha',2,1,'Int');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (2,'Barbarian','d12','simple Weapons, martial Weapons','light armor, medium armor, shields','Str, Con','Animal Handling, Athletics, Intimidation, Nature, Perception, Survival',2,'Str, Con, Dex, Wis, Cha, Int',NULL,NULL,NULL);
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (3,'Bard','d8','simple weapon, hand crossbow, longswords, rapiers, shortswords','light armor','Dex, Cha','Any',3,'Cha, Dex, Con, Wis, Int, Str',2,4,'Cha');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (4,'Blood Hunter','d10','simple Weapons, martial Weapons','light amror, medium armor, shields','Str, Wis','Athletics, Acrobatics, Arcana, Insight, Investigation, Survival',2,'Dex, Con, Wis, Int, Cha, Str',NULL,NULL,NULL);
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (5,'Cleric (Str)','d8','simple weapon','light armor, medium armor, shields','Wis, Cha','History, Insight, Medicinem, Persuasion, Religion',2,'Wis, Con, Str, Cha, Dex, Int',3,1,'Wis');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (6,'Druid','d8','clubs, daggers, darts, javelins, maces, quarterstaffs, scimitars, sickles, slings, spears','Light armor, medium armor, shields','Int, Wis','Arcana, Animal Handling, Insight, Medicin, Nature, Perception, Religion, Survival',2,'Wis, Con, Dex, Int, Str, Cha',2,1,'Wis');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (7,'Fighter','d10','simple weapon, martial weapon','light armor, medium armor, shields, heavy armor','Str, Con','Acrobatics, Animal Handling, Athletics, History, Insight, Intimidation, Perception, Survival',2,'Str, Con, Wis, Dex, Int, Cha',NULL,NULL,NULL);
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (8,'Monk','d8',NULL,NULL,'Str, Dex',NULL,2,'Dex, Con, Wis, Str, Int, Cha',NULL,NULL,NULL);
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (9,'Paladin','d10',NULL,NULL,'Wis, Cha',NULL,2,'Str, Cha, Con, Dex, Wis, Int',NULL,1,'Cha');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (10,'Ranger','d10',NULL,NULL,'Str, Dex',NULL,3,'Dex, Con, Wis, Int, Cha, Str',NULL,0,'Wis');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (11,'Rogue','d8',NULL,NULL,'Dex, Int',NULL,4,'Dex, Con, Wis, Int, Cha, Str',NULL,NULL,NULL);
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (12,'Sorcerer','d6',NULL,NULL,'Con, Cha',NULL,2,'Cha, Con, Dex, Int, Wis, Str',4,2,'Cha');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (13,'Warlock','d8',NULL,NULL,'Wis, Cha',NULL,2,'Cha, Con, Dex, Int, Wis, Str',2,2,'Cha');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (14,'Wizard','d6',NULL,NULL,'Int, Wis',NULL,2,'Int, Con, Dex, Wis, Cha, Str',3,1,'Int');
INSERT INTO class_model(Class_ID,Class_Name,class_Hit_Dice,class_Prof_Weapon,class_Prof_Armor,class_Saving_Throws,Class_Skills,Class_Skill_Number,class_Ability_Score_Distribution,class_Cantrips,class_Spells,class_Spell_Ability_Score) VALUES (15,'Commoner','d6',NULL,NULL,NULL,NULL,NULL,'random',NULL,NULL,NULL);

INSERT INTO job_model(Job_ID,Job_Name,Job_Skills,Job_Language) VALUES (1,'Acolyte','Insight, religion','2 of Choice');
INSERT INTO job_model(Job_ID,Job_Name,Job_Skills,Job_Language) VALUES (2,'Charlatan','Deception, Sleight of Hand',NULL);
INSERT INTO job_model(Job_ID,Job_Name,Job_Skills,Job_Language) VALUES (3,'Criminal','Deception, Stealth',NULL);
INSERT INTO job_model(Job_ID,Job_Name,Job_Skills,Job_Language) VALUES (4,'Entertainer','Acrobatics, Performance',NULL);
INSERT INTO job_model(Job_ID,Job_Name,Job_Skills,Job_Language) VALUES (5,'Guild Artisan','Insight, Persuasion','1 of Choice');

INSERT INTO language_model(Language_Id,Language_name) VALUES (1,'Common');
INSERT INTO language_model(Language_Id,Language_name) VALUES (2,'Dwarvish');
INSERT INTO language_model(Language_Id,Language_name) VALUES (3,'Elvish');
INSERT INTO language_model(Language_Id,Language_name) VALUES (4,'Giant');
INSERT INTO language_model(Language_Id,Language_name) VALUES (5,'Gnomisch');

INSERT INTO deity_model(Deity_ID,Deity_name,deity_Alignment) VALUES (1,'Abbathor','NE');
INSERT INTO deity_model(Deity_ID,Deity_name,deity_Alignment) VALUES (2,'Aegir','NE');
INSERT INTO deity_model(Deity_ID,Deity_name,deity_Alignment) VALUES (3,'Anubis','LN');
INSERT INTO deity_model(Deity_ID,Deity_name,deity_Alignment) VALUES (4,'Apep','NE');
INSERT INTO deity_model(Deity_ID,Deity_name,deity_Alignment) VALUES (5,'Apollo','CG');

INSERT INTO weapon_model(Weapon_ID,Weapon_Name,weapon_Property,weapon_Damage_Type,weapon_Damage_Dice,Weapon_Kind) VALUES (1,'Club','Light','bludgeoning','1d4','Simple Weapon');
INSERT INTO weapon_model(Weapon_ID,Weapon_Name,weapon_Property,weapon_Damage_Type,weapon_Damage_Dice,Weapon_Kind) VALUES (2,'Dagger','Finesse, light, thrown (20/60)','piercing','1d4','Simple Weapon');
INSERT INTO weapon_model(Weapon_ID,Weapon_Name,weapon_Property,weapon_Damage_Type,weapon_Damage_Dice,Weapon_Kind) VALUES (3,'Great Club','Two-handed','bludgeoning','1d8','Simple Weapon');
INSERT INTO weapon_model(Weapon_ID,Weapon_Name,weapon_Property,weapon_Damage_Type,weapon_Damage_Dice,Weapon_Kind) VALUES (4,'Handaxe','Light, thrown(20/60)','slashing','1d6','Simple Weapon');
INSERT INTO weapon_model(Weapon_ID,Weapon_Name,weapon_Property,weapon_Damage_Type,weapon_Damage_Dice,Weapon_Kind) VALUES (5,'Javelin','Thrown(30/120)','piercing','1d6','Simple Weapon');

INSERT INTO armor_model(Armor_Id,Armor_Name,armor_kind,armor_Ac,Armor_Max,Armor_Bonus,armor_Strength,armor_Stealth) VALUES (1,'Padded','Light Armor',11,NULL,'Dex',NULL,'Disadvantage');
INSERT INTO armor_model(Armor_Id,Armor_Name,armor_kind,armor_Ac,Armor_Max,Armor_Bonus,armor_Strength,armor_Stealth) VALUES (2,'Leather','Light Armor',11,NULL,'Dex',NULL,NULL);
INSERT INTO armor_model(Armor_Id,Armor_Name,armor_kind,armor_Ac,Armor_Max,Armor_Bonus,armor_Strength,armor_Stealth) VALUES (3,'Studded Leather','Light Armor',12,NULL,'Dex',NULL,NULL);
INSERT INTO armor_model(Armor_Id,Armor_Name,armor_kind,armor_Ac,Armor_Max,Armor_Bonus,armor_Strength,armor_Stealth) VALUES (4,'Hide','Medium Armor',12,14,'Dex',NULL,NULL);
INSERT INTO armor_model(Armor_Id,Armor_Name,armor_kind,armor_Ac,Armor_Max,Armor_Bonus,armor_Strength,armor_Stealth) VALUES (5,'Chain Shirt','Medium Armor',13,15,'Dex',NULL,NULL);

INSERT INTO Abili_Sc_Model(abili_Sc_id,abili_Sc_Name,abili_Sc_kind,abili_Sc) VALUES (1,'Random3d6Methode','Dice','3d6');
INSERT INTO Abili_Sc_Model(abili_Sc_id,abili_Sc_Name,abili_Sc_kind,abili_Sc) VALUES (2,'Random4d6Methode','Dice','4d6');
INSERT INTO Abili_Sc_Model(abili_Sc_id,abili_Sc_Name,abili_Sc_kind,abili_Sc) VALUES (3,'TerribleArray','Array','8,8,8,8,7,7');
INSERT INTO Abili_Sc_Model(abili_Sc_id,abili_Sc_Name,abili_Sc_kind,abili_Sc) VALUES (4,'Standard Array','Array','15,14,13,12,10,8');
INSERT INTO Abili_Sc_Model(abili_Sc_id,abili_Sc_Name,abili_Sc_kind,abili_Sc) VALUES (5,'MediumArray','Array','14,12,12,10,10,8');

INSERT INTO spell_model(Spell_Id,Spell_Name,spell_Level,spell_School,spell_Casting_Time,spell_Range,spell_Components,spell_Duration,spell_Description,spell_Level_Up_Scale,spell_Availability) VALUES (1,'Acid Splash','Cantrip','Conjuration','1 action','60 feet','V, S','Instantaneous','You hurl a bubble of acid. Choose one creature you can see within range, or choose two creatures you can see within range that are within 5 feet of each other. A target must succeed on a Dexterity saving throw or take 1d6 acid damage.','This spell''s damage increases by 1d6 when you reach 5th level (2d6), 11th level (3d6), and 17th level (4d6).','Artificer, Sorcerer, Wizard, Water Genasi');
INSERT INTO spell_model(Spell_Id,Spell_Name,spell_Level,spell_School,spell_Casting_Time,spell_Range,spell_Components,spell_Duration,spell_Description,spell_Level_Up_Scale,spell_Availability) VALUES (2,'Blade Ward','Cantrip','Abjuration','1 action','Self','V, S','1 round','You extend your hand and trace a sigil of warding in the air. Until the end of your next turn, you have resistance against bludgeoning, piercing, and slashing damage dealt by weapon attacks.',NULL,'Bard, Sorcerer, Warlock, Wizard, Earth Genasi');

INSERT INTO skill_model(Skill_Id,Skill_Name,skill_Ability_Score) VALUES (1,'Acrobatics','Dex');
INSERT INTO skill_model(Skill_Id,Skill_Name,skill_Ability_Score) VALUES (2,'Animal Handling','Wis');
INSERT INTO skill_model(Skill_Id,Skill_Name,skill_Ability_Score) VALUES (3,'Arcana','Int');
INSERT INTO skill_model(Skill_Id,Skill_Name,skill_Ability_Score) VALUES (4,'Athletics','Str');
INSERT INTO skill_model(Skill_Id,Skill_Name,skill_Ability_Score) VALUES (5,'Deception','Cha');

INSERT INTO level_model(Level_Id,Level_number,level_Prof_Bonus) VALUES (1,0,1);
INSERT INTO level_model(Level_Id,Level_number,level_Prof_Bonus) VALUES (2,1,2);
INSERT INTO level_model(Level_Id,Level_number,level_Prof_Bonus) VALUES (3,2,2);
INSERT INTO level_model(Level_Id,Level_number,level_Prof_Bonus) VALUES (4,3,2);
INSERT INTO level_model(Level_Id,Level_number,level_Prof_Bonus) VALUES (5,4,2);
INSERT INTO level_model(Level_Id,Level_number,level_Prof_Bonus) VALUES (6,5,3);

INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (1,'Lawful good','LG');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (2,'Neutral good','NG');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (3,'Chaotic good','CG');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (4,'Lawful neutral','LN');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (5,'Neutral','N');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (6,'Chaotic neutral','CN');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (7,'Lawful evil','LE');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (8,'Neutral evil','NE');
INSERT INTO alignment_model(Alignment_ID,Alignment_name,alignment_shorted) VALUES (9,'Chaotic evil','CE');

INSERT INTO history_model(history_Id, run_Var) VALUES (11,1);

INSERT INTO description_model(description_Id) VALUES (1);

INSERT INTO Name_Model(name_ID,name_First_Name,name_Last_Name,name_Race,name_Gender) VALUES (1,'Arix','Akambherylliax','Dragonborn','male');
INSERT INTO Name_Model(name_ID,name_First_Name,name_Last_Name,name_Race,name_Gender) VALUES (2,'Eks','Baharoosh','Dragonborn','female');
INSERT INTO Name_Model(name_ID,name_First_Name,name_Last_Name,name_Race,name_Gender) VALUES (3,'Anbera','Aranore','Dwarf','male');
INSERT INTO Name_Model(name_ID,name_First_Name,name_Last_Name,name_Race,name_Gender) VALUES (4,'Adrik','Balderk','Dwarf','male');
INSERT INTO Name_Model(name_ID,name_First_Name,name_Last_Name,name_Race,name_Gender) VALUES (5,'Ael','Aloro','Elf','female');
