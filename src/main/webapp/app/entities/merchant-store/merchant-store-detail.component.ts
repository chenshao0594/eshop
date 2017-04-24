import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

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

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private merchantStoreService: MerchantStoreService,
        private route: ActivatedRoute
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

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMerchantStores() {
        this.eventSubscriber = this.eventManager.subscribe('merchantStoreListModification', (response) => this.load(this.merchantStore.id));
    }
}
