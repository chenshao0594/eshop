import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductRelationship } from './product-relationship.model';
import { ProductRelationshipService } from './product-relationship.service';

@Component({
    selector: 'jhi-product-relationship-detail',
    templateUrl: './product-relationship-detail.component.html'
})
export class ProductRelationshipDetailComponent implements OnInit, OnDestroy {

    productRelationship: ProductRelationship;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productRelationshipService: ProductRelationshipService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productRelationship']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductRelationships();
    }

    load(id) {
        this.productRelationshipService.find(id).subscribe((productRelationship) => {
            this.productRelationship = productRelationship;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productRelationship.id !== undefined) {
            this.productRelationshipService.update(this.productRelationship)
                .subscribe((res: ProductRelationship) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productRelationshipService.create(this.productRelationship)
                .subscribe((res: ProductRelationship) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductRelationship) {
        this.eventManager.broadcast({ name: 'productRelationshipModification', content: 'OK'});
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

    registerChangeInProductRelationships() {
        this.eventSubscriber = this.eventManager.subscribe('productRelationshipListModification', (response) => this.load(this.productRelationship.id));
    }
}
