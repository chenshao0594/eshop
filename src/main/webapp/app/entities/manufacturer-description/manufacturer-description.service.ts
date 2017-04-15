import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ManufacturerDescription } from './manufacturer-description.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ManufacturerDescriptionService {

    private resourceUrl = 'api/manufacturer-descriptions';
    private resourceSearchUrl = 'api/manufacturer-descriptions/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(manufacturerDescription: ManufacturerDescription): Observable<ManufacturerDescription> {
        const copy: ManufacturerDescription = Object.assign({}, manufacturerDescription);
        copy.dateLastClick = this.dateUtils
            .convertLocalDateToServer(manufacturerDescription.dateLastClick);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(manufacturerDescription: ManufacturerDescription): Observable<ManufacturerDescription> {
        const copy: ManufacturerDescription = Object.assign({}, manufacturerDescription);
        copy.dateLastClick = this.dateUtils
            .convertLocalDateToServer(manufacturerDescription.dateLastClick);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ManufacturerDescription> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateLastClick = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateLastClick);
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
            jsonResponse[i].dateLastClick = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateLastClick);
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
