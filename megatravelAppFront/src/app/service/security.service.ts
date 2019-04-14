import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SubjectSoftwareModel} from '../model/subjectSoftware.model';
import {CertificateModel} from '../model/certificate.model';
import {CertificateBackModel} from '../model/certificateBack.model';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class SecurityService {

  private BASE_URL = 'http://localhost:8080/api/security';

  constructor(private http: HttpClient) {
  }

  getSubjectSoftware(): Observable<SubjectSoftwareModel[]> {
    return this.http.get<SubjectSoftwareModel[]>( `${this.BASE_URL}/getSubjectSoftware`, httpOptions);
  }


  addCertificate(model: CertificateModel, email: string): Observable<any> {
    const data = JSON.stringify(model);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post( 'http://localhost:8080/api/security/createCertificate/' + email, data, {headers: headers});
  }

  getCert(email: string): Observable<CertificateBackModel[]> {
    return this.http.get<CertificateBackModel[]>( `${this.BASE_URL}/getCertificates/` + email, httpOptions);
  }

  checkCommunication(string1: string, string2: string): Observable<any> {
    return this.http.get('http://localhost:8080/api/security/communicate/' + string1 + '/' + string2, httpOptions);
  }


}
