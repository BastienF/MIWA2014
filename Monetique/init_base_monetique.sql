insert into LOYALTY_CARD_TYPE (CARD_TYPE_CODE, MONTLY_CREDIT_LIMIT, TOTAL_CREDIT_LIMIT, ECHELON_NB) 
values ("myBronze", 500, 1500, 2);
insert into LOYALTY_CARD_TYPE (CARD_TYPE_CODE, MONTLY_CREDIT_LIMIT, TOTAL_CREDIT_LIMIT, ECHELON_NB) 
values ("mySilver", 1000, 3000, 3);

insert into FIDELITY_CREDIT_ACCOUNT (ID_LOYALTY_CARD_TYPE, CUSTOMER_CODE, IS_BLACKLISTED, BLAKLISTED_DATE, TOTAL_CREDIT_AMOUNT, TOTAL_REPAID_CREDIT__AMOUNT, IS_DELETED) 
values 
(1, "38167037", false, null, 0, 0, false), 
(1, "68523705", false, null, 0, 0, false),
(1, "84876870", false, null, 0, 0, false),
(2, "74398835", false, null, 0, 0, false),
(2, "72648238", false, null, 0, 0, false);