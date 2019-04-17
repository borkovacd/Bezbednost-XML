import {SubjectSoftwareModel} from './subjectSoftware.model';

export class CertificateBackModel {
    public  id: number;
    public  startDate: Date;
    public  endDate: Date;
    public  serialNumber: number;
    public  revoked: boolean;
    public  issuerSoft: SubjectSoftwareModel;
    public  subSoft: SubjectSoftwareModel;
}
