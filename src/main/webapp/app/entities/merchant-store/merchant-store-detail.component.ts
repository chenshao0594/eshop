import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { MerchantStore } from './merchant-store.model';
import { MerchantStoreService } from './merchant-store.service';

@Component({
    selector: 'jhi-merchant-store-detail',
    templateUrl: './merchant-store-detail.component.html'
})
export class MerchantStoreDetailComponent implements OnInit, OnDestroy {

    merchantStore: MerchantStore;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private merchantStoreService: MerchantStoreService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['merchantStore']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMerchantStores();
    }

    load(id) {
        this.merchantStoreService.find(id).subscribe((merchantStore) => {
            this.merchantStore = merchantStore;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.merchantStore.id !== undefined) {
            this.merchantStoreService.update(this.merchantStore)
                .subscribe((res: MerchantStore) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.merchantStoreService.create(this.merchantStore)
                .subscribe((res: MerchantStore) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MerchantStore) {
        this.eventManager.broadcast({ name: 'merchantStoreModification', content: 'OK'});
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

    registerChangeInMerchantStores() {
        this.eventSubscriber = this.eventManager.subscribe('merchantStoreListModification', (response) => this.load(this.merchantStore.id));
    }
}
