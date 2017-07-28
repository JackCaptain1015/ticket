package com.tqmall.ticket.common.excel;


import com.tqmall.ticket.common.excel.vo.ExcelDefinition;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * 导出Excel,标题之前的信息
 * @author wurenzhi
 *
 */
public interface ExcelHeader {

	/**
	 * 构建标题的信息
	 * @param sheet				Excel中的sheet页
	 * @param excelDefinition	XML中定义的信息
	 * @param beans				导出的数据
	 * @param workbook			可用于调整样式
	 */
	void buildHeader(Sheet sheet, ExcelDefinition excelDefinition, List<?> beans, Workbook workbook);
}
