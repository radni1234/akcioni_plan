/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AkcioniPlanTestModule } from '../../../test.module';
import { KriterijumBodovanjeDetailComponent } from 'app/entities/kriterijum-bodovanje/kriterijum-bodovanje-detail.component';
import { KriterijumBodovanje } from 'app/shared/model/kriterijum-bodovanje.model';

describe('Component Tests', () => {
    describe('KriterijumBodovanje Management Detail Component', () => {
        let comp: KriterijumBodovanjeDetailComponent;
        let fixture: ComponentFixture<KriterijumBodovanjeDetailComponent>;
        const route = ({ data: of({ kriterijumBodovanje: new KriterijumBodovanje(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AkcioniPlanTestModule],
                declarations: [KriterijumBodovanjeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(KriterijumBodovanjeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KriterijumBodovanjeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.kriterijumBodovanje).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
