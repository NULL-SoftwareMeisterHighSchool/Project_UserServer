package setting.common.controller;

import setting.common.domain.CommonException;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;


public class RestControllerContext
{
	public ResponseEntity<?> execute(RestControllerStrategy strategy)
	{
		try
		{
			Object data = strategy.execute();
			
			return ResponseEntity.ok(data);
		}
		catch (CommonException e)
		{	
			LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);

			return ResponseEntity.internalServerError().body(e.getMessage());
		}
		catch (Exception e)
		{
			LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
			
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
