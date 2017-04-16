import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ShoppingCartAttributeItem } from './shopping-cart-attribute-item.model';
import { ShoppingCartAttributeItemService } from './shopping-cart-attribute-item.service';

@Component({
    selector: 'jhi-shopping-cart-attribute-item-detail',
    templateUrl: './shopping-cart-attribute-item-detail.component.html'
})
export class ShoppingCartAttributeItemDetailComponent implements OnInit, OnDestroy {

    shoppingCartAttributeItem: ShoppingCartAttributeItem;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private shoppingCartAttributeItemService: ShoppingCartAttributeItemService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['shoppingCartAttributeItem']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShoppingCartAttributeItems();
    }

    load(id) {
        this.shoppingCartAttributeItemService.find(id).subscribe((shoppingCartAttributeItem) => {
            this.shoppingCartAttributeItem = shoppingCartAttributeItem;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.shoppingCartAttributeItem.id !== undefined) {
            this.shoppingCartAttributeItemService.update(this.shoppingCartAttributeItem)
                .subscribe((res: ShoppingCartAttributeItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.shoppingCartAttributeItemService.create(this.shoppingCartAttributeItem)
                .subscribe((res: ShoppingCartAttributeItem) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ShoppingCartAttributeItem) {
        this.eventManager.broadcast({ name: 'shoppingCartAttributeItemModification', content: 'OK'});
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

    registerChangeInShoppingCartAttributeItems() {
        this.eventSubscriber = this.eventManager.subscribe('shoppingCartAttributeItemListModification', (response) => this.load(this.shoppingCartAttributeItem.id));
    }
}
