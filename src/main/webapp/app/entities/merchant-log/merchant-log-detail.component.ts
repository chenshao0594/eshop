import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { MerchantLog } from './merchant-log.model';
import { MerchantLogService } from './merchant-log.service';

@Component({
    selector: 'jhi-merchant-log-detail',
    templateUrl: './merchant-log-detail.component.html'
})
export class MerchantLogDetailComponent implements OnInit, OnDestroy {

    merchantLog: MerchantLog;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private merchantLogService: MerchantLogService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['merchantLog']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMerchantLogs();
    }

    load(id) {
        this.merchantLogService.find(id).subscribe((merchantLog) => {
            this.merchantLog = merchantLog;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.merchantLog.id !== undefined) {
            this.merchantLogService.update(this.merchantLog)
                .subscribe((res: MerchantLog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.merchantLogService.create(this.merchantLog)
                .subscribe((res: MerchantLog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MerchantLog) {
        this.eventManager.broadcast({ name: 'merchantLogModification', content: 'OK'});
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

    registerChangeInMerchantLogs() {
        this.eventSubscriber = this.eventManager.subscribe('merchantLogListModification', (response) => this.load(this.merchantLog.id));
    }
}
