CREATE TABLE IF NOT EXISTS `countries` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
    `code` char(2) NOT NULL,
    `name` varchar(100) NOT NULL,
    `flag_url` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4;

INSERT INTO `countries` (`id`, `code`, `name`, `flag_url`) VALUES
   (1, 'AF', 'Afghanistan', 'https://flagcdn.com/af.svg'),
   (2, 'AX', 'Åland Islands', 'https://flagcdn.com/ax.svg'),
   (3, 'AL', 'Albania', 'https://flagcdn.com/al.svg'),
   (4, 'DZ', 'Algeria', 'https://flagcdn.com/dz.svg'),
   (5, 'AS', 'American Samoa', 'https://flagcdn.com/as.svg'),
   (6, 'AD', 'Andorra', 'https://flagcdn.com/ad.svg'),
   (7, 'AO', 'Angola', 'https://flagcdn.com/ao.svg'),
   (8, 'AI', 'Anguilla', 'https://flagcdn.com/ai.svg'),
   (9, 'AQ', 'Antarctica', 'https://flagcdn.com/aq.svg'),
   (10, 'AG', 'Antigua & Barbuda', 'https://flagcdn.com/ag.svg'),
   (11, 'AR', 'Argentina', 'https://flagcdn.com/ar.svg'),
   (12, 'AM', 'Armenia', 'https://flagcdn.com/am.svg'),
   (13, 'AW', 'Aruba', 'https://flagcdn.com/aw.svg'),
   (14, 'AU', 'Australia', 'https://flagcdn.com/au.svg'),
   (15, 'AT', 'Austria', 'https://flagcdn.com/at.svg'),
   (16, 'AZ', 'Azerbaijan', 'https://flagcdn.com/az.svg'),
   (17, 'BS', 'Bahamas', 'https://flagcdn.com/bs.svg'),
   (18, 'BH', 'Bahrain', 'https://flagcdn.com/bh.svg'),
   (19, 'BD', 'Bangladesh', 'https://flagcdn.com/bd.svg'),
   (20, 'BB', 'Barbados', 'https://flagcdn.com/bb.svg'),
   (21, 'BY', 'Belarus', 'https://flagcdn.com/by.svg'),
   (22, 'BE', 'Belgium', 'https://flagcdn.com/be.svg'),
   (23, 'BZ', 'Belize', 'https://flagcdn.com/bz.svg'),
   (24, 'BJ', 'Benin', 'https://flagcdn.com/bj.svg'),
   (25, 'BM', 'Bermuda', 'https://flagcdn.com/bm.svg'),
   (26, 'BT', 'Bhutan', 'https://flagcdn.com/bt.svg'),
   (27, 'BO', 'Bolivia', 'https://flagcdn.com/bo.svg'),
   (28, 'BA', 'Bosnia & Herzegovina', 'https://flagcdn.com/ba.svg'),
   (29, 'BW', 'Botswana', 'https://flagcdn.com/bw.svg'),
   (30, 'BV', 'Bouvet Island', 'https://flagcdn.com/bv.svg'),
   (31, 'BR', 'Brazil', 'https://flagcdn.com/br.svg'),
   (32, 'IO', 'British Indian Ocean Territory', 'https://flagcdn.com/io.svg'),
   (33, 'BN', 'Brunei', 'https://flagcdn.com/bn.svg'),
   (34, 'BG', 'Bulgaria', 'https://flagcdn.com/bg.svg'),
   (35, 'BF', 'Burkina Faso', 'https://flagcdn.com/bf.svg'),
   (36, 'BI', 'Burundi', 'https://flagcdn.com/bi.svg'),
   (37, 'CV', 'Cape Verde', 'https://flagcdn.com/cv.svg'),
   (38, 'KH', 'Cambodia', 'https://flagcdn.com/kh.svg'),
   (39, 'CM', 'Cameroon', 'https://flagcdn.com/cm.svg'),
   (40, 'CA', 'Canada', 'https://flagcdn.com/ca.svg'),
   (41, 'BQ', 'Caribbean Netherlands', 'https://flagcdn.com/bq.svg'),
   (42, 'KY', 'Cayman Islands', 'https://flagcdn.com/ky.svg'),
   (43, 'CF', 'Central African Republic', 'https://flagcdn.com/cf.svg'),
   (44, 'TD', 'Chad', 'https://flagcdn.com/td.svg'),
   (45, 'CL', 'Chile', 'https://flagcdn.com/cl.svg'),
   (46, 'CN', 'China', 'https://flagcdn.com/cn.svg'),
   (47, 'CX', 'Christmas Island', 'https://flagcdn.com/cx.svg'),
   (48, 'CC', 'Cocos (Keeling) Islands', 'https://flagcdn.com/cc.svg'),
   (49, 'CO', 'Colombia', 'https://flagcdn.com/co.svg'),
   (50, 'KM', 'Comoros', 'https://flagcdn.com/km.svg'),
   (51, 'CG', 'Congo - Brazzaville', 'https://flagcdn.com/cg.svg'),
   (52, 'CD', 'Congo - Kinshasa', 'https://flagcdn.com/cd.svg'),
   (53, 'CK', 'Cook Islands', 'https://flagcdn.com/ck.svg'),
   (54, 'CR', 'Costa Rica', 'https://flagcdn.com/cr.svg'),
   (55, 'HR', 'Croatia', 'https://flagcdn.com/hr.svg'),
   (56, 'CU', 'Cuba', 'https://flagcdn.com/cu.svg'),
   (57, 'CW', 'Curaçao', 'https://flagcdn.com/cw.svg'),
   (58, 'CY', 'Cyprus', 'https://flagcdn.com/cy.svg'),
   (59, 'CZ', 'Czechia', 'https://flagcdn.com/w320/cz.png'),
   (60, 'CI', 'Côte d’Ivoire', 'https://flagcdn.com/w320/ci.png'),
   (61, 'DK', 'Denmark', 'https://flagcdn.com/w320/dk.png'),
   (62, 'DJ', 'Djibouti', 'https://flagcdn.com/w320/dj.png'),
   (63, 'DM', 'Dominica', 'https://flagcdn.com/w320/dm.png'),
   (64, 'DO', 'Dominican Republic', 'https://flagcdn.com/w320/do.png'),
   (65, 'EC', 'Ecuador', 'https://flagcdn.com/w320/ec.png'),
   (66, 'EG', 'Egypt', 'https://flagcdn.com/w320/eg.png'),
   (67, 'SV', 'El Salvador', 'https://flagcdn.com/w320/sv.png'),
   (68, 'GQ', 'Equatorial Guinea', 'https://flagcdn.com/w320/gq.png'),
   (69, 'ER', 'Eritrea', 'https://flagcdn.com/w320/er.png'),
   (70, 'EE', 'Estonia', 'https://flagcdn.com/w320/ee.png'),
   (71, 'SZ', 'Eswatini', 'https://flagcdn.com/w320/sz.png'),
   (72, 'ET', 'Ethiopia', 'https://flagcdn.com/w320/et.png'),
   (73, 'FK', 'Falkland Islands (Islas Malvinas)', 'https://flagcdn.com/w320/fk.png'),
   (74, 'FO', 'Faroe Islands', 'https://flagcdn.com/w320/fo.png'),
   (75, 'FJ', 'Fiji', 'https://flagcdn.com/w320/fj.png'),
   (76, 'FI', 'Finland', 'https://flagcdn.com/w320/fi.png'),
   (77, 'FR', 'France', 'https://flagcdn.com/w320/fr.png'),
   (78, 'GF', 'French Guiana', 'https://flagcdn.com/w320/gf.png'),
   (79, 'PF', 'French Polynesia', 'https://flagcdn.com/w320/pf.png'),
   (80, 'TF', 'French Southern Territories', 'https://flagcdn.com/w320/tf.png'),
   (81, 'GA', 'Gabon', 'https://flagcdn.com/w320/ga.png'),
   (82, 'GM', 'Gambia', 'https://flagcdn.com/w320/gm.png'),
   (83, 'GE', 'Georgia', 'https://flagcdn.com/w320/ge.png'),
   (84, 'DE', 'Germany', 'https://flagcdn.com/w320/de.png'),
   (85, 'GH', 'Ghana', 'https://flagcdn.com/w320/gh.png'),
   (86, 'GI', 'Gibraltar', 'https://flagcdn.com/w320/gi.png'),
   (87, 'GR', 'Greece', 'https://flagcdn.com/w320/gr.png'),
   (88, 'GL', 'Greenland', 'https://flagcdn.com/w320/gl.png'),
   (89, 'GD', 'Grenada', 'https://flagcdn.com/w320/gd.png'),
   (90, 'GP', 'Guadeloupe', 'https://flagcdn.com/w320/gp.png'),
   (91, 'GU', 'Guam', 'https://flagcdn.com/w320/gu.png'),
   (92, 'GT', 'Guatemala', 'https://flagcdn.com/w320/gt.png'),
   (93, 'GG', 'Guernsey', 'https://flagcdn.com/w320/gg.png'),
   (94, 'GN', 'Guinea', 'https://flagcdn.com/w320/gn.png'),
   (95, 'GW', 'Guinea-Bissau', 'https://flagcdn.com/w320/gw.png'),
   (96, 'GY', 'Guyana', 'https://flagcdn.com/w320/gy.png'),
   (97, 'HT', 'Haiti', 'https://flagcdn.com/w320/ht.png'),
   (98, 'HM', 'Heard & McDonald Islands', 'https://flagcdn.com/w320/hm.png'),
   (99, 'HN', 'Honduras', 'https://flagcdn.com/w320/hn.png'),
   (100, 'HK', 'Hong Kong', 'https://flagcdn.com/w320/hk.png'),
   (101, 'HU', 'Hungary', 'https://flagcdn.com/w320/hu.png'),
   (102, 'IS', 'Iceland', 'https://flagcdn.com/w320/is.png'),
   (103, 'IN', 'India', 'https://flagcdn.com/w320/in.png'),
   (104, 'ID', 'Indonesia', 'https://flagcdn.com/w320/id.png'),
   (105, 'IR', 'Iran', 'https://flagcdn.com/w320/ir.png'),
   (106, 'IQ', 'Iraq', 'https://flagcdn.com/w320/iq.png'),
   (107, 'IE', 'Ireland', 'https://flagcdn.com/w320/ie.png'),
   (108, 'IM', 'Isle of Man', 'https://flagcdn.com/w320/im.png'),
   (109, 'IL', 'Israel', 'https://flagcdn.com/w320/il.png'),
   (110, 'IT', 'Italy', 'https://flagcdn.com/w320/it.png'),
   (111, 'JM', 'Jamaica', 'https://flagcdn.com/w320/jm.png'),
   (112, 'JP', 'Japan', 'https://flagcdn.com/w320/jp.png'),
   (113, 'JE', 'Jersey', 'https://flagcdn.com/w320/je.png'),
   (114, 'JO', 'Jordan', 'https://flagcdn.com/w320/jo.png'),
   (115, 'KZ', 'Kazakhstan', 'https://flagcdn.com/w320/kz.png'),
   (116, 'KE', 'Kenya', 'https://flagcdn.com/w320/ke.png'),
   (117, 'KI', 'Kiribati', 'https://flagcdn.com/w320/ki.png'),
   (118, 'KP', 'North Korea', 'https://flagcdn.com/w320/kp.png'),
   (119, 'KR', 'South Korea', 'https://flagcdn.com/w320/kr.png'),
   (120, 'XK', 'Kosovo', 'https://flagcdn.com/w320/xk.png'),
   (121, 'KW', 'Kuwait', 'https://flagcdn.com/w320/kw.png'),
   (122, 'KG', 'Kyrgyzstan', 'https://flagcdn.com/w320/kg.png'),
   (123, 'LA', 'Laos', 'https://flagcdn.com/w320/la.png'),
   (124, 'LV', 'Latvia', 'https://flagcdn.com/w320/lv.png'),
   (125, 'LB', 'Lebanon', 'https://flagcdn.com/w320/lb.png'),
   (126, 'LS', 'Lesotho', 'https://flagcdn.com/w320/ls.png'),
   (127, 'LR', 'Liberia', 'https://flagcdn.com/w320/lr.png'),
   (128, 'LY', 'Libya', 'https://flagcdn.com/w320/ly.png'),
   (129, 'LI', 'Liechtenstein', 'https://flagcdn.com/w320/li.png'),
   (130, 'LT', 'Lithuania', 'https://flagcdn.com/w320/lt.png'),
   (131, 'LU', 'Luxembourg', 'https://flagcdn.com/w320/lu.png'),
   (132, 'MO', 'Macao', 'https://flagcdn.com/w320/mo.png'),
   (133, 'MK', 'North Macedonia', 'https://flagcdn.com/w320/mk.png'),
   (134, 'MG', 'Madagascar', 'https://flagcdn.com/w320/mg.png'),
   (135, 'MW', 'Malawi', 'https://flagcdn.com/w320/mw.png'),
   (136, 'MY', 'Malaysia', 'https://flagcdn.com/w320/my.png'),
   (137, 'MV', 'Maldives', 'https://flagcdn.com/w320/mv.png'),
   (138, 'ML', 'Mali', 'https://flagcdn.com/w320/ml.png'),
   (139, 'MT', 'Malta', 'https://flagcdn.com/w320/mt.png'),
   (140, 'MH', 'Marshall Islands', 'https://flagcdn.com/w320/mh.png'),
   (141, 'MQ', 'Martinique', 'https://flagcdn.com/w320/mq.png'),
   (142, 'MR', 'Mauritania', 'https://flagcdn.com/w320/mr.png'),
   (143, 'MU', 'Mauritius', 'https://flagcdn.com/w320/mu.png'),
   (144, 'YT', 'Mayotte', 'https://flagcdn.com/w320/yt.png'),
   (145, 'MX', 'Mexico', 'https://flagcdn.com/w320/mx.png'),
   (146, 'FM', 'Micronesia', 'https://flagcdn.com/w320/fm.png'),
   (147, 'MD', 'Moldova', 'https://flagcdn.com/w320/md.png'),
   (148, 'MC', 'Monaco', 'https://flagcdn.com/w320/mc.png'),
   (149, 'MN', 'Mongolia', 'https://flagcdn.com/w320/mn.png'),
   (150, 'ME', 'Montenegro', 'https://flagcdn.com/w320/me.png'),
   (151, 'MS', 'Montserrat', 'https://flagcdn.com/w320/ms.png'),
   (152, 'MA', 'Morocco', 'https://flagcdn.com/w320/ma.png'),
   (153, 'MZ', 'Mozambique', 'https://flagcdn.com/w320/mz.png'),
   (154, 'MM', 'Myanmar (Burma)', 'https://flagcdn.com/w320/mm.png'),
   (155, 'NA', 'Namibia', 'https://flagcdn.com/w320/na.png'),
   (156, 'NR', 'Nauru', 'https://flagcdn.com/w320/nr.png'),
   (157, 'NP', 'Nepal', 'https://flagcdn.com/w320/np.png'),
   (158, 'NL', 'Netherlands', 'https://flagcdn.com/w320/nl.png'),
   (159, 'NC', 'New Caledonia', 'https://flagcdn.com/w320/nc.png'),
   (160, 'NZ', 'New Zealand', 'https://flagcdn.com/w320/nz.png'),
   (161, 'NI', 'Nicaragua', 'https://flagcdn.com/w320/ni.png'),
   (162, 'NE', 'Niger', 'https://flagcdn.com/w320/ne.png'),
   (163, 'NG', 'Nigeria', 'https://flagcdn.com/w320/ng.png'),
   (164, 'NU', 'Niue', 'https://flagcdn.com/w320/nu.png'),
   (165, 'NF', 'Norfolk Island', 'https://flagcdn.com/w320/nf.png'),
   (166, 'MP', 'Northern Mariana Islands', 'https://flagcdn.com/w320/mp.png'),
   (167, 'NO', 'Norway', 'https://flagcdn.com/w320/no.png'),
   (168, 'OM', 'Oman', 'https://flagcdn.com/w320/om.png'),
   (169, 'PK', 'Pakistan', 'https://flagcdn.com/w320/pk.png'),
   (170, 'PW', 'Palau', 'https://flagcdn.com/w320/pw.png'),
   (171, 'PS', 'Palestine', 'https://flagcdn.com/w320/ps.png'),
   (172, 'PA', 'Panama', 'https://flagcdn.com/w320/pa.png'),
   (173, 'PG', 'Papua New Guinea', 'https://flagcdn.com/w320/pg.png'),
   (174, 'PY', 'Paraguay', 'https://flagcdn.com/w320/py.png'),
   (175, 'PE', 'Peru', 'https://flagcdn.com/w320/pe.png'),
   (176, 'PH', 'Philippines', 'https://flagcdn.com/w320/ph.png'),
   (177, 'PN', 'Pitcairn Islands', 'https://flagcdn.com/w320/pn.png'),
   (178, 'PL', 'Poland', 'https://flagcdn.com/w320/pl.png'),
   (179, 'PT', 'Portugal', 'https://flagcdn.com/w320/pt.png'),
   (180, 'PR', 'Puerto Rico', 'https://flagcdn.com/w320/pr.png'),
   (181, 'QA', 'Qatar', 'https://flagcdn.com/w320/qa.png'),
   (182, 'RE', 'Réunion', 'https://flagcdn.com/w320/re.png'),
   (183, 'RO', 'Romania', 'https://flagcdn.com/w320/ro.png'),
   (184, 'RU', 'Russia', 'https://flagcdn.com/w320/ru.png'),
   (185, 'RW', 'Rwanda', 'https://flagcdn.com/w320/rw.png'),
   (186, 'BL', 'St. Barthélemy', 'https://flagcdn.com/w320/bl.png'),
   (187, 'SH', 'St. Helena', 'https://flagcdn.com/w320/sh.png'),
   (188, 'KN', 'St. Kitts & Nevis', 'https://flagcdn.com/w320/kn.png'),
   (189, 'LC', 'St. Lucia', 'https://flagcdn.com/w320/lc.png'),
   (190, 'MF', 'St. Martin', 'https://flagcdn.com/w320/mf.png'),
   (191, 'PM', 'St. Pierre & Miquelon', 'https://flagcdn.com/w320/pm.png'),
   (192, 'VC', 'St. Vincent & Grenadines', 'https://flagcdn.com/w320/vc.png'),
   (193, 'WS', 'Samoa', 'https://flagcdn.com/w320/ws.png'),
   (194, 'SM', 'San Marino', 'https://flagcdn.com/w320/sm.png'),
   (195, 'ST', 'São Tomé & Príncipe', 'https://flagcdn.com/w320/st.png'),
   (196, 'SA', 'Saudi Arabia', 'https://flagcdn.com/w320/sa.png'),
    (197, 'SN', 'Senegal', 'https://flagcdn.com/w320/sn.png'),
    (198, 'RS', 'Serbia', 'https://flagcdn.com/w320/rs.png'),
    (199, 'SC', 'Seychelles', 'https://flagcdn.com/w320/sc.png'),
    (200, 'SL', 'Sierra Leone', 'https://flagcdn.com/w320/sl.png'),
    (201, 'SG', 'Singapore', 'https://flagcdn.com/w320/sg.png'),
    (202, 'SX', 'Sint Maarten', 'https://flagcdn.com/w320/sx.png'),
    (203, 'SK', 'Slovakia', 'https://flagcdn.com/w320/sk.png'),
    (204, 'SI', 'Slovenia', 'https://flagcdn.com/w320/si.png'),
    (205, 'SB', 'Solomon Islands', 'https://flagcdn.com/w320/sb.png'),
    (206, 'SO', 'Somalia', 'https://flagcdn.com/w320/so.png'),
    (207, 'ZA', 'South Africa', 'https://flagcdn.com/w320/za.png'),
    (208, 'GS', 'South Georgia & South Sandwich Islands', 'https://flagcdn.com/w320/gs.png'),
    (209, 'SS', 'South Sudan', 'https://flagcdn.com/w320/ss.png'),
    (210, 'ES', 'Spain', 'https://flagcdn.com/w320/es.png'),
    (211, 'LK', 'Sri Lanka', 'https://flagcdn.com/w320/lk.png'),
    (212, 'SD', 'Sudan', 'https://flagcdn.com/w320/sd.png'),
    (213, 'SR', 'Suriname', 'https://flagcdn.com/w320/sr.png'),
    (214, 'SJ', 'Svalbard & Jan Mayen', 'https://flagcdn.com/w320/sj.png'),
    (215, 'SE', 'Sweden', 'https://flagcdn.com/w320/se.png'),
    (216, 'CH', 'Switzerland', 'https://flagcdn.com/w320/ch.png'),
    (217, 'SY', 'Syria', 'https://flagcdn.com/w320/sy.png'),
    (218, 'TW', 'Taiwan', 'https://flagcdn.com/w320/tw.png'),
    (219, 'TJ', 'Tajikistan', 'https://flagcdn.com/w320/tj.png'),
    (220, 'TZ', 'Tanzania', 'https://flagcdn.com/w320/tz.png'),
    (221, 'TH', 'Thailand', 'https://flagcdn.com/w320/th.png'),
    (222, 'TL', 'Timor-Leste', 'https://flagcdn.com/w320/tl.png'),
    (223, 'TG', 'Togo', 'https://flagcdn.com/w320/tg.png'),
    (224, 'TK', 'Tokelau', 'https://flagcdn.com/w320/tk.png'),
    (225, 'TO', 'Tonga', 'https://flagcdn.com/w320/to.png'),
    (226, 'TT', 'Trinidad & Tobago', 'https://flagcdn.com/w320/tt.png'),
    (227, 'TN', 'Tunisia', 'https://flagcdn.com/w320/tn.png'),
    (228, 'TR', 'Türkiye', 'https://flagcdn.com/w320/tr.png'),
    (229, 'TM', 'Turkmenistan', 'https://flagcdn.com/w320/tm.png'),
    (230, 'TC', 'Turks & Caicos Islands', 'https://flagcdn.com/w320/tc.png'),
    (231, 'TV', 'Tuvalu', 'https://flagcdn.com/w320/tv.png'),
    (232, 'UG', 'Uganda', 'https://flagcdn.com/w320/ug.png'),
    (233, 'UA', 'Ukraine', 'https://flagcdn.com/w320/ua.png'),
    (234, 'AE', 'United Arab Emirates', 'https://flagcdn.com/w320/ae.png'),
    (235, 'GB', 'United Kingdom', 'https://flagcdn.com/w320/gb.png'),
    (236, 'US', 'United States', 'https://flagcdn.com/w320/us.png'),
    (237, 'UY', 'Uruguay', 'https://flagcdn.com/w320/uy.png'),
    (238, 'UZ', 'Uzbekistan', 'https://flagcdn.com/w320/uz.png'),
   (239, 'VU', 'Vanuatu', 'https://flagcdn.com/w320/vu.png'),
   (240, 'VA', 'Vatican City', 'https://flagcdn.com/w320/va.png'),
   (241, 'VE', 'Venezuela', 'https://flagcdn.com/w320/ve.png'),
   (242, 'VN', 'Vietnam', 'https://flagcdn.com/w320/vn.png'),
   (243, 'VG', 'British Virgin Islands', 'https://flagcdn.com/w320/vg.png'),
   (244, 'VI', 'U.S. Virgin Islands', 'https://flagcdn.com/w320/vi.png'),
   (245, 'WF', 'Wallis & Futuna', 'https://flagcdn.com/w320/wf.png'),
   (246, 'EH', 'Western Sahara', 'https://flagcdn.com/w320/eh.png'),
   (247, 'YE', 'Yemen', 'https://flagcdn.com/w320/ye.png'),
   (248, 'ZM', 'Zambia', 'https://flagcdn.com/w320/zm.png'),
   (249, 'ZW', 'Zimbabwe', 'https://flagcdn.com/w320/zw.png')