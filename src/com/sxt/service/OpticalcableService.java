package com.sxt.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sxt.dao.OpticalcableDao;
import com.sxt.po.CoreUsed;
import com.sxt.po.Opticalcable;
import com.sxt.po.OpticalcableAnalysis;
import com.sxt.po.OpticalcableCore;
import com.sxt.po.OpticalcableCoreTree;
import com.sxt.po.OpticalcableSearch;
import com.sxt.po.Pager;
import com.sxt.utils.ExportUtil;

@Service("opticalcableService")
public class OpticalcableService {
	
	private OpticalcableDao opticalcableDao;
	
	public void add(Opticalcable b){
		opticalcableDao.add(b);
	}
	
	public void exportExcel(String hql, String[] titles, ServletOutputStream outputStream)
	{
		List<Opticalcable> os = this.opticalcableDao.findAll();
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet("纤芯使用情况分析");
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		int i = 0;
		for (i = 0; i < titles.length; i++)
		{
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (os != null && os.size() > 0)
		{
			int index = 0;
			List<OpticalcableAnalysis> oa = new ArrayList<OpticalcableAnalysis>();
			for (i = 0; i < os.size(); i ++)
			{
				OpticalcableAnalysis temp = new OpticalcableAnalysis();
				List<CoreUsed> cu = this.opticalcableDao.findAllCore(os.get(i).getId());

				int used = 0, j = 0;
				for (j = 0; j < cu.size(); j ++)
				{
					if (cu.get(j).getA_type() != 0 || cu.get(j).getZ_type() != 0)
					{
						used ++;
					}
				}
				temp.setUsedCounts(used);
				double rate = (double)used / (double)os.get(i).getCoreCounts();
				BigDecimal df = new BigDecimal(rate);
		        rate = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				temp.setUsedPercent(rate);
				oa.add(temp);
				
				for (j = 0; j < cu.size(); j++)
				{
					XSSFRow bodyRow = sheet.createRow(index + 1);
					CoreUsed cud = cu.get(j);
					
					cell = bodyRow.createCell(0);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(os.get(i).getName());
					
					String cell2 = "";
					if (os.get(i).getStart_box() != null)
					{
						cell2 = os.get(i).getStart_box().getBox_name();
					}
					else
					{
						cell2 = os.get(i).getStartAddress();
					}
					
					cell = bodyRow.createCell(1);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(cell2);
					
					
					String cell3 = "";
					if (os.get(i).getEnd_box() != null)
					{
						cell3 = os.get(i).getEnd_box().getBox_name();
					}
					else
					{
						cell3 = os.get(i).getEndAddress();
					}
					
					cell = bodyRow.createCell(2);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(cell3);
					
					cell = bodyRow.createCell(3);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(os.get(i).getCoreCounts());
					
					cell = bodyRow.createCell(4);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(used);
					
					
					cell = bodyRow.createCell(5);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(rate);
					
					cell = bodyRow.createCell(6);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(cu.get(j).getCore().getSequence());
					
					String cell7 = "";
					
					if (cu.get(j).getA_type() == 1) cell7 = cu.get(j).getA_string();
					else if (cu.get(j).getA_type() == 2) cell7 = cu.get(j).getA_terminal().getName();
					else if (cu.get(j).getA_type() == 3) cell7 = cu.get(j).getA_core().getName();
					
					cell = bodyRow.createCell(7);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(cell7);
					
					String cell8 = "";
					
					if (cu.get(j).getZ_type() == 1) cell8 = cu.get(j).getZ_string();
					else if (cu.get(j).getZ_type() == 2) cell8 = cu.get(j).getZ_terminal().getName();
					else if (cu.get(j).getZ_type() == 3) cell8 = cu.get(j).getZ_core().getName();
					
					cell = bodyRow.createCell(8);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(cell8);
					
					index ++;
				}
			}
		}
		try
		{
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				outputStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}
	
	//铅芯分页
	public List<CoreUsed> findCoreByPager(Pager bis){
		return opticalcableDao.findCoreByPager(bis);
	}
	
	//光缆分页
	public List<OpticalcableAnalysis> findByPager(OpticalcableSearch bis){
		List<Opticalcable> os = this.opticalcableDao.findByPager(bis);
		List<OpticalcableAnalysis> oa = new ArrayList<OpticalcableAnalysis>();
		for (int i = 0; i < os.size(); i ++)
		{
			OpticalcableAnalysis temp = new OpticalcableAnalysis();
			temp.setId(os.get(i).getId());
			temp.setCoreCounts(os.get(i).getCoreCounts());
			temp.setEnd_box(os.get(i).getEnd_box());
			temp.setEndAddress(os.get(i).getEndAddress());
			temp.setName(os.get(i).getName());
			temp.setRemarks(os.get(i).getRemarks());
			temp.setStart_box(os.get(i).getStart_box());
			temp.setStartAddress(os.get(i).getStartAddress());
			temp.setType(os.get(i).getType());
			List<CoreUsed> cu = this.opticalcableDao.findAllCore(os.get(i).getId());
			int used = 0;
			for (int j = 0; j < cu.size(); j ++)
			{
				if (cu.get(j).getA_type() != 0 || cu.get(j).getZ_type() != 0)
				{
					used ++;
				}
			}
			temp.setUsedCounts(used);
			double rate = (double)used / (double)os.get(i).getCoreCounts();
			BigDecimal df = new BigDecimal(rate);
	        rate = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			temp.setUsedPercent(rate);
			oa.add(temp);
		}
		return oa;
	}
	
	public Opticalcable find(int id)
	{
		return opticalcableDao.find(id);
	}
	
	public void update(Opticalcable b)
	{
		opticalcableDao.update(b);
	}
	
	public void delete(int[] ids) {
		opticalcableDao.delete(ids);
	}
	
	//获取光缆数量
	public int findCounts(OpticalcableSearch bis)
	{
		return opticalcableDao.findCounts(bis);
	}
	
	//获取铅芯分页数量
	public int findCoreCounts(Pager bis)
	{
		return opticalcableDao.findCoreCounts(bis);
	}
	
	//获取全部光缆
	public List<Opticalcable> findAll(){
		return this.opticalcableDao.findAll();
	}
	
	//获取全部光缆
	public List<Opticalcable> findAllByBoxId(int id){
		return this.opticalcableDao.findAllByBoxId(id);
	}
	
	//获取全部光缆和纤芯
	public List<OpticalcableCoreTree> findAllTreeByBoxId(int id){
		return this.opticalcableDao.findAllTreeByBoxId(id);
	}
	
	//获取全部光缆和纤芯for工单
		public List<OpticalcableCore> findAllOPCoreByBoxId(int id){
			return this.opticalcableDao.findAllOPCoreByBoxId(id);
		}
		
		
	
	//获取光缆全部纤芯
	public List<CoreUsed> findAllCore(int id){
		return this.opticalcableDao.findAllCore(id);
	}

	//获取光缆全部可用纤芯
	public List<CoreUsed> findAllCoreByBoxId(int boxid, int optiid){
		return this.opticalcableDao.findAllCoreByBoxId(boxid, optiid);
	}
	
	public OpticalcableDao getOpticalcableDao() {
		return opticalcableDao;
	}

	@Resource
	public void setOpticalcableDao(OpticalcableDao opticalcableDao) {
		this.opticalcableDao = opticalcableDao;
	}

	
}
