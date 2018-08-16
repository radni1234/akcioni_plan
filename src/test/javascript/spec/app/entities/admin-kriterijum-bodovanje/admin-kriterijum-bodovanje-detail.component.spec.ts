/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { AdminKriterijumBodovanjeDetailComponent } from 'app/entities/admin-kriterijum-bodovanje/admin-kriterijum-bodovanje-detail.component';
import { AdminKriterijumBodovanje } from 'app/shared/model/admin-kriterijum-bodovanje.model';

describe('Component Tests', () => {
    describe('AdminKriterijumBodovanje Management Detail Component', () => {
        let comp: AdminKriterijumBodovanjeDetailComponent;
        let fixture: ComponentFixture<AdminKriterijumBodovanjeDetailComponent>;
        const route = ({ data: of({ adminKriterijumBodovanje: new AdminKriterijumBodovanje(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [AdminKriterijumBodovanjeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdminKriterijumBodovanjeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdminKriterijumBodovanjeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.adminKriterijumBodovanje).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
