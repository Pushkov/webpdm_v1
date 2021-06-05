package nicomed.webpdm.utils;

import nicomed.webpdm.models.DocFormat;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.models.PlanPoint;
import nicomed.webpdm.service.PdmDocumentService;

public class SheetUtils {


    public static int getDocumentSheetsCount(PdmDocument document) {
        int count = 0;
        if (document.getFormats() != null)
            for (DocFormat df : document.getFormats()) {
                if(!df.getFormat().getName().equals("БЧ"))
                    count += df.getQty();
            }
        return count;
    }

    public static float getDocumentSheetsA1(PdmDocument document) {
        float count = 0;
        if (document.getFormats() != null)
            for (DocFormat df : document.getFormats()) {
                count += df.getQty() * df.getSizeA1();
            }
        return count;
    }

    public static int getPlanPointSheetsCount(PlanPoint point, PdmDocumentService sevice) {
        int count = 0;
        float a1 = 0;
        for (PdmDocument document : sevice.findAllByPlanpoint(point)) {
            count += SheetUtils.getDocumentSheetsCount(document);
            a1 += SheetUtils.getDocumentSheetsA1(document);
        }
        return count;
    }

    public static float getPlanPointSheetsA1(PlanPoint point, PdmDocumentService sevice) {
        int count = 0;
        float a1 = 0;
        for (PdmDocument document : sevice.findAllByPlanpoint(point)) {
            count += SheetUtils.getDocumentSheetsCount(document);
            a1 += SheetUtils.getDocumentSheetsA1(document);
        }
        return a1;
    }

    public static int[] getDocumentSheets(PdmDocument document) {

        int A4 = 0;
        int A43 = 0;
        int A3 = 0;
        int A33 = 0;
        int A2 = 0;
        int A23 = 0;
        int A1 = 0;

        if (document.getFormats() != null)
            for (DocFormat df : document.getFormats()) {
                switch (df.getFormat().getName()) {
                    case "А4":
                        A4 += df.getQty();
                        break;
                    case "А4х3":
                        A43 += df.getQty();
                        break;
                    case "А3":
                        A3 += df.getQty();
                        break;
                    case "А3х3":
                        A33 += df.getQty();
                        break;
                    case "А2":
                        A2 += df.getQty();
                        break;
                    case "А2х3":
                        A23 += df.getQty();
                        break;
                    case "А1":
                        A1 += df.getQty();
                        break;
                    default:
                        break;
                }
            }

        int[] count = {A4, A43, A3, A33, A2, A23, A1};
        return count;
    }

    public static int[] getPlanPointSheets(PlanPoint point, PdmDocumentService sevice) {
        int A4 = 0;
        int A43 = 0;
        int A3 = 0;
        int A33 = 0;
        int A2 = 0;
        int A23 = 0;
        int A1 = 0;
        int[] res = {A4, A43, A3, A33, A2, A23, A1};

        for (PdmDocument document : sevice.findAllByPlanpoint(point)) {
             int[] count = SheetUtils.getDocumentSheets(document);
             for (int i = 0 ; i < count.length; i++){
                 res[i] = res[i] + count[i];
             }
        }
        return res;
    }
}
