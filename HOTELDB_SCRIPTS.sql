CREATE PROCEDURE SP_TBL_RESERVAS_INSERT
    @HABITACION_CODIGO VARCHAR(50),
    @FECHA_ENTRADA DATE,
    @FECHA_SALIDA DATE,
	@DIAS INT,
    @DNI VARCHAR(50),
    @NOMBRE_CLIENTE VARCHAR(250),
    @TIPO_PAGO VARCHAR(50),
    @PRECIO NUMERIC(18, 2),
	@TOTAL NUMERIC(18, 2)
AS
BEGIN
    SET NOCOUNT ON;

	DECLARE @MESSAGE VARCHAR(500)

    IF EXISTS (
		SELECT * FROM TBL_RESERVAS
		WHERE HABITACION_CODIGO = @HABITACION_CODIGO AND ANULADA = 'N' AND
			(@FECHA_ENTRADA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR 
			@FECHA_SALIDA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR
			FECHA_ENTRADA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA OR
			FECHA_SALIDA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA)
	)
	BEGIN
		SELECT TOP 1
			@MESSAGE = CONCAT(
			'El periodo de fechas indicado colisiona con la reserva #',RESERVA_ID,
			' (desde ',FORMAT(FECHA_ENTRADA,'dd-MM-yyyy'), ' hasta ',FORMAT(FECHA_SALIDA,'dd-MM-yyyy')
			)
		FROM TBL_RESERVAS
		WHERE HABITACION_CODIGO = @HABITACION_CODIGO AND ANULADA = 'N' AND 
			(@FECHA_ENTRADA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR 
			@FECHA_SALIDA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR
			FECHA_ENTRADA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA OR
			FECHA_SALIDA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA)
		RAISERROR(@MESSAGE, 16, 1);
		RETURN;
	END

    INSERT INTO TBL_RESERVAS (
        HABITACION_CODIGO, FECHA_ENTRADA, FECHA_SALIDA, DIAS,
        DNI, NOMBRE_CLIENTE, TIPO_PAGO, PRECIO,TOTAL
    )
    VALUES (
        @HABITACION_CODIGO, @FECHA_ENTRADA, @FECHA_SALIDA, @DIAS,
        @DNI, @NOMBRE_CLIENTE, @TIPO_PAGO, @PRECIO, @TOTAL
    );
END

GO

CREATE PROCEDURE SP_TBL_RESERVAS_UPDATE
	@RESERVA_ID INT,
    @FECHA_ENTRADA DATE,
    @FECHA_SALIDA DATE,
	@DIAS INT,
    @DNI VARCHAR(50),
    @NOMBRE_CLIENTE VARCHAR(250),
    @TIPO_PAGO VARCHAR(50),
	@TOTAL NUMERIC(18, 2)
AS
BEGIN
    SET NOCOUNT ON;

	DECLARE @MESSAGE VARCHAR(500)
	DECLARE @HABITACION_CODIGO VARCHAR(50) = (
		SELECT TOP 1 HABITACION_CODIGO FROM TBL_RESERVAS WHERE RESERVA_ID = @RESERVA_ID
	)

    IF EXISTS (
		SELECT *
		FROM TBL_RESERVAS
		WHERE HABITACION_CODIGO = @HABITACION_CODIGO AND RESERVA_ID <> @RESERVA_ID AND
			(@FECHA_ENTRADA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR 
			@FECHA_SALIDA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR
			FECHA_ENTRADA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA OR
			FECHA_SALIDA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA)
	)
	BEGIN
		SELECT TOP 1
			@MESSAGE = CONCAT(
			'El periodo de fechas indicado colisiona con la reserva #',RESERVA_ID,
			' (desde ',FORMAT(FECHA_ENTRADA,'dd-MM-yyyy'), ' hasta ',FORMAT(FECHA_SALIDA,'dd-MM-yyyy')
			)
		FROM TBL_RESERVAS
		WHERE HABITACION_CODIGO = @HABITACION_CODIGO AND RESERVA_ID <> @RESERVA_ID AND 
			(@FECHA_ENTRADA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR 
			@FECHA_SALIDA BETWEEN FECHA_ENTRADA AND FECHA_SALIDA OR
			FECHA_ENTRADA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA OR
			FECHA_SALIDA BETWEEN @FECHA_ENTRADA AND @FECHA_SALIDA)
		RAISERROR(@MESSAGE, 16, 1);
		RETURN;
	END

    UPDATE TBL_RESERVAS 
	SET FECHA_ENTRADA = @FECHA_ENTRADA, FECHA_SALIDA = @FECHA_SALIDA, 
		DIAS = @DIAS, DNI = @DNI, NOMBRE_CLIENTE = @NOMBRE_CLIENTE, 
		TIPO_PAGO = @TIPO_PAGO, TOTAL = @TOTAL
	WHERE RESERVA_ID = @RESERVA_ID
END