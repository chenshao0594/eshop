import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ShippingOrigin } from './shipping-origin.model';
import { ShippingOriginService } from './shipping-origin.service';

@Component({
    selector: 'jhi-shipping-origin-detail',
    templateUrl: './shipping-origin-detail.component.html'
})
export class ShippingOriginDetailComponent implements OnInit, OnDestroy {

    shippingOrigin: ShippingOrigin;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private shippingOriginService: ShippingOriginService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['shippingOrigin']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShippingOrigins();
    }

    load(id) {
        this.shippingOriginService.find(id).subscribe((shippingOrigin) => {
            this.shippingOrigin = shippingOrigin;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.shippingOrigin.id !== undefined) {
            this.shippingOriginService.update(this.shippingOrigin)
                .subscribe((res: ShippingOrigin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.shippingOriginService.create(this.shippingOrigin)
                .subscribe((res: ShippingOrigin) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ShippingOrigin) {
        this.eventManager.broadcast({ name: 'shippingOriginModification', content: 'OK'});
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

    registerChangeInShippingOrigins() {
        this.eventSubscriber = this.eventManager.subscribe('shippingOriginListModification', (response) => this.load(this.shippingOrigin.id));
    }
}
