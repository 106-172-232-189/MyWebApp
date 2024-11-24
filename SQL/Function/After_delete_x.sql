CREATE OR REPLACE FUNCTION public.After_delete_x(
)
RETURNS VOID
LANGUAGE 'sql'
AS $BODY$
	UPDATE Racing_umamusume AS A SET no = B.no
	FROM (SELECT ROW_NUMBER() OVER (ORDER BY no) AS no, name FROM Racing_umamusume) AS B
	WHERE A.name = B.name;
	SELECT Setval('Seq2', (SELECT COUNT(*) FROM Racing_Umamusume));
$BODY$;