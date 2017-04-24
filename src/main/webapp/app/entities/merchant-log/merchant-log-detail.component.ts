import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private merchantLogService: MerchantLogService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMerchantLogs() {
        this.eventSubscriber = this.eventManager.subscribe('merchantLogListModification', (response) => this.load(this.merchantLog.id));
    }
}
