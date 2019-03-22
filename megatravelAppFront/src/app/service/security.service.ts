import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {SubjectSoftwareModel} from '../model/subjectSoftware.model';

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


}
