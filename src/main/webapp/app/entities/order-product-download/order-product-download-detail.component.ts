import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderProductDownload } from './order-product-download.model';
import { OrderProductDownloadService } from './order-product-download.service';

@Component({
    selector: 'jhi-order-product-download-detail',
    templateUrl: './order-product-download-detail.component.html'
})
export class OrderProductDownloadDetailComponent implements OnInit, OnDestroy {

    orderProductDownload: OrderProductDownload;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderProductDownloadService: OrderProductDownloadService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderProductDownload']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderProductDownloads();
    }

    load(id) {
        this.orderProductDownloadService.find(id).subscribe((orderProductDownload) => {
            this.orderProductDownload = orderProductDownload;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderProductDownload.id !== undefined) {
            this.orderProductDownloadService.update(this.orderProductDownload)
                .subscribe((res: OrderProductDownload) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductDownloadService.create(this.orderProductDownload)
                .subscribe((res: OrderProductDownload) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProductDownload) {
        this.eventManager.broadcast({ name: 'orderProductDownloadModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderProductDownloads() {
        this.eventSubscriber = this.eventManager.subscribe('orderProductDownloadListModification', (response) => this.load(this.orderProductDownload.id));
    }
}
