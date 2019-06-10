import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SubjectSoftwareModel} from '../model/subjectSoftware.model';
import {AllSubjectSoftwareModel} from '../model/allSubjectSoftware.model';
import {CertificateModel} from '../model/certificate.model';
import {CertificateBackModel} from '../model/certificateBack.model';
import {getResponseURL} from '@angular/http/src/http_utils';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class SecurityService {

  private BASE_URL = 'https://localhost:8092/api/security';

  constructor(private http: HttpClient) {
  }

  getSubjectSoftware(): Observable<SubjectSoftwareModel[]> {
    return this.http.get<SubjectSoftwareModel[]>( `${this.BASE_URL}/getSubjectSoftware`, httpOptions);
  }

  getAllSubjectSoftwares(email: string): Observable<AllSubjectSoftwareModel[]> {
    return this.http.get<AllSubjectSoftwareModel[]>(`${this.BASE_URL}/getAllSubjectSoftwares/` + localStorage.getItem('loggedUser'), httpOptions);
  }


  addCertificate(model: CertificateModel, token: string): Observable<any> {
    const data = JSON.stringify(model);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post( 'https://localhost:8092/api/security/createCertificate/' + token, data, {headers: headers});
  }

  getCert(token: string): Observable<CertificateBackModel[]> {
    return this.http.get<CertificateBackModel[]>( `${this.BASE_URL}/getCertificates/` + token, httpOptions);
  }

  checkCommunication(string1: string, string2: string): Observable<any> {
    return this.http.get('https://localhost:8092/api/security/communicate/' + string1 + '/' + string2, httpOptions);
  }

  revokeCert(serialNumber: any , message: string): Observable<any> {
    return this.http.post(`${this.BASE_URL}/revokeCertificate/${serialNumber}/${message}`, httpOptions);

  }

  getAllCert(): Observable<CertificateBackModel[]> {
    return this.http.get<CertificateBackModel[]>( `${this.BASE_URL}/getAllCertificates`, httpOptions);
  }

  getMyCert(token: string): Observable<CertificateBackModel> {
    return this.http.get<CertificateBackModel>(`${this.BASE_URL}/getMyCertificate/${token}`, httpOptions);
  }

}
