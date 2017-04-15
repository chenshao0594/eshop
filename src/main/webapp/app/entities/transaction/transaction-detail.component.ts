import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Transaction } from './transaction.model';
import { TransactionService } from './transaction.service';

@Component({
    selector: 'jhi-transaction-detail',
    templateUrl: './transaction-detail.component.html'
})
export class TransactionDetailComponent implements OnInit, OnDestroy {

    transaction: Transaction;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private transactionService: TransactionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['transaction', 'transactionType', 'paymentType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTransactions();
    }

    load(id) {
        this.transactionService.find(id).subscribe((transaction) => {
            this.transaction = transaction;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.transaction.id !== undefined) {
            this.transactionService.update(this.transaction)
                .subscribe((res: Transaction) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.transactionService.create(this.transaction)
                .subscribe((res: Transaction) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Transaction) {
        this.eventManager.broadcast({ name: 'transactionModification', content: 'OK'});
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

    registerChangeInTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('transactionListModification', (response) => this.load(this.transaction.id));
    }
}
