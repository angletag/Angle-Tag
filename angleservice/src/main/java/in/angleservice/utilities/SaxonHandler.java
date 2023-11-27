package in.angleservice.utilities;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import net.sf.saxon.s9api.StaticError;
import net.sf.saxon.s9api.XmlProcessingError;

@Component
public class SaxonHandler {

	private final Logger log = LoggerFactory.getLogger(SaxonHandler.class);

	public String processError(List<XmlProcessingError> errors) {
		StringBuilder sb = new StringBuilder();
		if (!CollectionUtils.isEmpty(errors)) {
			errors.forEach(error -> {
				String error_code = error.getErrorCode().getLocalName();
				String error_mess = error.getMessage();
				String error_line_column = "at Path " + error.getPath();
				sb.append(error_code).append(": ").append(error_mess).append(" ").append(error_line_column)
						.append("\n");
			});
		}
		log.error(sb.toString());
		return sb.toString();
	}
}
