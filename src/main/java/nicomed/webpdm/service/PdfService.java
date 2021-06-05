package nicomed.webpdm.service;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import nicomed.webpdm.enums.MyEnum;
import nicomed.webpdm.models.PlanPoint;
import nicomed.webpdm.utils.SheetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/pdf_res/";

    private final SpringTemplateEngine springTemplateEngine;
    private DesignerService designerService;
    private PlanPointServise planPointServise;
    private PdmDocumentService documentService;

    @Autowired
    public PdfService(SpringTemplateEngine springTemplateEngine,
                      PlanPointServise planPointServise,
                      PdmDocumentService documentService,
                      DesignerService designerService) {
        this.springTemplateEngine = springTemplateEngine;
        this.designerService = designerService;
        this.planPointServise = planPointServise;
        this.documentService = documentService;
    }

    public File generatePdf(Long id) throws IOException, DocumentException {
        Context context = getContext(id);
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }

    private File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("Card_", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.getFontResolver().addFont("fonts/calibri.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }


    private Context getContext(Long pp_id) {
        Context context = new Context();
        PlanPoint pp = planPointServise.getOne(pp_id);
        context.setVariable("ppName", pp.getName());
        context.setVariable("ppDocDes", pp.getDesignation());
        context.setVariable("ppWorkshop", pp.getWorkshop().getName());
        context.setVariable("ppSheets", SheetUtils.getPlanPointSheetsCount(pp,documentService));
        context.setVariable("ppA1", SheetUtils.getPlanPointSheetsA1(pp,documentService));
        context.setVariable("ppDesigner", pp.getDesigner().getLastName());
        context.setVariable("ppMnOffice", pp.getDesigner().getOffice().getBoss().getLastName());

        if (pp.getBasics().contains("с/з") || pp.getBasics().contains("С/З"))
                context.setVariable("ppBasics", pp.getBasics());
        else
            context.setVariable("ppBasicsTz", pp.getBasics());

        context.setVariable("ppInPlan", pp.getOrderInPlan());
        context.setVariable("ppMonth", MyEnum.MonthLONG().get(pp.getPlanmonth().getMonth()));
        int[] sheets = SheetUtils.getPlanPointSheets(pp,documentService);
        context.setVariable("ppSheetA4", sheets[0] == 0 ? " " : String.valueOf(sheets[0]));
        context.setVariable("ppSheetA4_3", sheets[1] == 0 ? " " : String.valueOf(sheets[1]));
        context.setVariable("ppSheetA3", sheets[2] == 0 ? " " : String.valueOf(sheets[2]));
        context.setVariable("ppSheetA3_3", sheets[3] == 0 ? " " : String.valueOf(sheets[3]));
        context.setVariable("ppSheetA2", sheets[4] == 0 ? " " : String.valueOf(sheets[4]));
        context.setVariable("ppSheetA2_3", sheets[5] == 0 ? " " : String.valueOf(sheets[5]));
        context.setVariable("ppSheetA1", sheets[6] == 0 ? " " : String.valueOf(sheets[6]));
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return springTemplateEngine.process("planPointCard", context);
    }
}
