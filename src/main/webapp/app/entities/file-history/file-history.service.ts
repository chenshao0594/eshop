import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { FileHistory } from './file-history.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class FileHistoryService {

    private resourceUrl = 'api/file-histories';
    private resourceSearchUrl = 'api/file-histories/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(fileHistory: FileHistory): Observable<FileHistory> {
        const copy: FileHistory = Object.assign({}, fileHistory);
        copy.dateAdded = this.dateUtils
            .convertLocalDateToServer(fileHistory.dateAdded);
        copy.dateDeleted = this.dateUtils
            .convertLocalDateToServer(fileHistory.dateDeleted);
        copy.accountedDate = this.dateUtils
            .convertLocalDateToServer(fileHistory.accountedDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(fileHistory: FileHistory): Observable<FileHistory> {
        const copy: FileHistory = Object.assign({}, fileHistory);
        copy.dateAdded = this.dateUtils
            .convertLocalDateToServer(fileHistory.dateAdded);
        copy.dateDeleted = this.dateUtils
            .convertLocalDateToServer(fileHistory.dateDeleted);
        copy.accountedDate = this.dateUtils
            .convertLocalDateToServer(fileHistory.accountedDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<FileHistory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateAdded = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateAdded);
            jsonResponse.dateDeleted = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateDeleted);
            jsonResponse.accountedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.accountedDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].dateAdded = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateAdded);
            jsonResponse[i].dateDeleted = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateDeleted);
            jsonResponse[i].accountedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].accountedDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
